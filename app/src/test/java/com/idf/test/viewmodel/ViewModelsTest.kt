package com.idf.test.viewmodel

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    UserDetailsViewModelTest::class,
    UserListViewModelTest::class
)
class ViewModelsTest