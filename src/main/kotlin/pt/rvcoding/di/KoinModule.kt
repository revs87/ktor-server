package pt.rvcoding.di

import org.koin.dsl.module
import pt.rvcoding.domain.repository.AppointmentRepository
import pt.rvcoding.domain.repository.AuthRepository
import pt.rvcoding.domain.repository.UserRepository
import pt.rvcoding.repository.AppointmentRepositoryImpl
import pt.rvcoding.repository.AuthRepositoryImpl
import pt.rvcoding.repository.PPKGenerator
import pt.rvcoding.repository.UserRepositoryImpl

val koinModule = module {
    single { PPKGenerator() } // use factory for safety
    single<AuthRepository> { AuthRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<AppointmentRepository> { AppointmentRepositoryImpl() }
}