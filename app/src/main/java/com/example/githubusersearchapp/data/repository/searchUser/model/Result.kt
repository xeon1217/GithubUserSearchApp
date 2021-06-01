package com.example.githubusersearchapp.data.repository.searchUser.model

import java.lang.Exception


/**
 * 코루틴에서의 결과값을 전달해주는 클래스
 * 데이터가 정상적으로 발행되었을 경우 data에 실림
 * 오류 발생시 동봉된 exception으로 오류제어
 */

data class Result<T>(
    var data: T? = null,
    var exception: Exception? = null,
)

data class Error(
    var exception: Exception,
    var errorCode: Int?,
    var errorBody: String?,
    var message: String?
)