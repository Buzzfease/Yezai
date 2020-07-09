package com.jywl.yezai.utils

import android.content.Context
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.RequestExecutor
import com.yanzhenjie.permission.runtime.Permission

/**
 * created by Buzz
 * on 2020/7/9
 * email lmx2060918@126.com
 */
object EasyPermission {
    /**
     * 单个权限
     */
    fun checkSingle(context: Context, onGranted: OnGranted,
                    onDenied: OnDenied, onRationale: OnRationale,
                    onAlwaysDenied: OnAlwaysDenied, vararg permissions: String?) {
        //有权限需要申请
        if (permissions.isNotEmpty()) {
            AndPermission.with(context)
                .runtime()
                .permission(permissions) //当用户拒绝一次，在下次请求时，我们应该展示为什么需要此权限的说明
                .rationale { ctx, data, executor ->
                    val sb = transferPermission(ctx, data)
                    onRationale.onRationale(sb.toString(), executor)
                } //用户同意
                .onGranted { onGranted.onGranted() } //用户拒绝
                .onDenied { pm ->
                    onDenied.onDenied()
                    /**
                     * 当用户点击应用程序的某个按钮，而他又总是拒绝我们需要的某个权限时，
                     * 应用程序可能不会响应（但不是ANR），为了避免这种情况，
                     * 我们应该在用户总是拒绝某个权限时提示用户去系统设置中授权哪些权限给我们，
                     * 无论用户是否真的会授权给我们。
                     */
                    /**
                     * 当用户点击应用程序的某个按钮，而他又总是拒绝我们需要的某个权限时，
                     * 应用程序可能不会响应（但不是ANR），为了避免这种情况，
                     * 我们应该在用户总是拒绝某个权限时提示用户去系统设置中授权哪些权限给我们，
                     * 无论用户是否真的会授权给我们。
                     */
                    if (AndPermission.hasAlwaysDeniedPermission(context, pm)) {
                        //permissions总是被拒绝了
                        val sb = transferPermission(context, pm)
                        onAlwaysDenied.onAlwaysDenied(sb.toString())
                    }
                }
                .start()
        } else {
            //无需申请权限
            onGranted.onGranted()
        }
    }

    private fun transferPermission(context: Context, permissionList: List<String>): StringBuilder {
        val deniedList: List<String> =
            Permission.transformText(context, permissionList)
        val sb = StringBuilder()
        for (permission in deniedList) {
            sb.append(permission)
            sb.append(",")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb
    }

    interface OnGranted {
        fun onGranted()
    }

    interface OnDenied {
        fun onDenied()
    }

    interface OnRationale {
        fun onRationale(
            permissionStr: String?,
            executor: RequestExecutor?
        )
    }

    interface OnAlwaysDenied {
        fun onAlwaysDenied(permissionStr: String?)
    }
}