package com.github.ui.detail

import android.os.Bundle
import com.github.R
import com.github.common.otherwise
import com.github.common.yes
import com.github.network.GraphQlService
import com.github.network.entities.Repository
import com.github.network.services.ActivityService
import com.github.network.services.RepositoryService
import com.github.util.*
import kotlinx.android.synthetic.main.activity_repo_details.*
import retrofit2.Response
import rx.Subscriber

class RepoDetailActivity : BaseDetailSwipeFinishActivity() {
    companion object {
        const val ARG_REPO = "argument_repository"
    }

    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)// 左上角返回键

        repository = intent.getParcelableExtra(ARG_REPO)!!

        initDetails()
        reloadDetails()
    }

    private fun initDetails() {
        avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
        collapsingToolbar.title = repository.name

        descriptionView.markdownText = getString(R.string.repo_description_template,
            repository.owner.login,
            repository.owner.html_url,
            repository.name,
            repository.html_url,
            repository.owner.login,
            repository.owner.html_url,
            githubTimeToDate(repository.created_at).view())

        bodyView.text = repository.description

        detailContainer.alpha = 0f

        stars.checkEvent = { isChecked ->
            isChecked.yes {
                ActivityService.unstar(repository.owner.login, repository.name).map { false }
            }.otherwise {
                ActivityService.star(repository.owner.login, repository.name).map { true }
            }.doOnNext {
                reloadDetails(true)
            }
        }

        watches.checkEvent = { isChecked ->
            isChecked.yes {
                ActivityService.unwatch(repository.owner.login, repository.name).map { false }
            }.otherwise {
                ActivityService.watch(repository.owner.login, repository.name).map { true }
            }.doOnNext {
                reloadDetails(true)
            }
        }

        ActivityService.isStarred(repository.owner.login, repository.name)
            .onErrorReturn {
                if (it is retrofit2.HttpException) {
                    it.response() as Response<Any>
                } else {
                    throw it
                }
            }
            .subscribeIgnoreError {
                stars.isChecked = it.isSuccessful
            }

        ActivityService.isWatched(repository.owner.login, repository.name)
            .subscribeIgnoreError {
                watches.isChecked = it.subscribed
            }
    }

    private fun reloadDetails(forceNetwork: Boolean = false) {
        RepositoryService.getRepository(repository.owner.login, repository.name, forceNetwork)
            .subscribe(object : Subscriber<Repository>() {
                override fun onStart() {
                    loadingView.animate().alpha(1f).start()
                }

                override fun onNext(t: Repository) {
                    repository = t

                    owner.content = repository.owner.login
                    stars.content = repository.stargazers_count.toString()
                    watches.content = repository.subscribers_count.toString()
                    forks.content = repository.forks_count.toString()
                    issues.content = repository.open_issues_count.toString()

                    loadingView.animate().alpha(0f).start()
                    detailContainer.animate().alpha(1f).start()
                }

                override fun onCompleted() = Unit

                override fun onError(e: Throwable) {
                    loadingView.animate().alpha(0f).start()
                    e.printStackTrace()
                }

            })

        GraphQlService.repositoryIssuesCount(repository.owner.login, repository.name)
            .subscribeIgnoreError { data ->
                issues.content = "open: ${
                    data.repository()?.openIssues()?.totalCount() ?: 0
                } closed: ${
                    data.repository()?.closedIssues()?.totalCount() ?: 0
                }"
            }
    }

}