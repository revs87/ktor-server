package pt.rvcoding.di

import org.koin.dsl.module
import pt.rvcoding.domain.auth.AuthRepository
import pt.rvcoding.repository.AuthRepositoryImpl
import pt.rvcoding.repository.PPKGenerator

val koinModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single { PPKGenerator() } // use factory for safety
}