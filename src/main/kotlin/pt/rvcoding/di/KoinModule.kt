package pt.rvcoding.di

import org.koin.dsl.module
import pt.rvcoding.domain.AuthRepository
import pt.rvcoding.repository.AuthRepositoryImpl

val koinModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
}