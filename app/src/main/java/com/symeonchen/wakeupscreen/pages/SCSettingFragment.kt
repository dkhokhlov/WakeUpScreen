package com.symeonchen.wakeupscreen.pages

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.symeonchen.wakeupscreen.SCBaseFragment
import com.symeonchen.wakeupscreen.data.SCConstant
import com.symeonchen.wakeupscreen.views.SCSettingItem
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_layout_setting.*


class SCSettingFragment : SCBaseFragment() {

    private var alertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.symeonchen.wakeupscreen.R.layout.fragment_layout_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        refresh()
    }

    private fun setListener() {
        item_setting_wake_screen_time.listener = object : SCSettingItem.OnItemClickListener {
            override fun onItemCLick() {
                initWakeScreenTimeDialog()
            }
        }

        item_setting_proximity_detect.listener = object : SCSettingItem.OnItemClickListener {
            override fun onItemCLick() {
                initProximitySwitchDialog()
            }
        }

    }

    private fun refresh() {
        val sec = MMKV.defaultMMKV().getInt(SCConstant.WAKE_SCREEN_SECOND, 2)
        item_setting_wake_screen_time.bindData(
            null,
            "${sec}s"
        )

        val switch = MMKV.defaultMMKV().getBoolean(SCConstant.PROXIMITY_SWITCH, true)
        item_setting_proximity_detect.bindData(
            null,
            if (switch) "已开启" else "已关闭"

        )
    }

    private fun initWakeScreenTimeDialog() {
        alertDialog?.dismiss()
        val builder = AlertDialog.Builder(context!!)
        val secList = arrayOf("1s", "2s", "3s", "4s", "5s")
        val checkedItem: Int = MMKV.defaultMMKV().getInt(SCConstant.WAKE_SCREEN_SECOND, 2) - 1
        var index = checkedItem
        alertDialog = builder.setSingleChoiceItems(
            secList, checkedItem
        ) { _, which -> index = which }
            .setPositiveButton("确定") { _, _ ->
                MMKV.defaultMMKV().putInt(SCConstant.WAKE_SCREEN_SECOND, index + 1)
                refresh()
            }
            .create().apply { show() }
    }

    private fun initProximitySwitchDialog() {
        alertDialog?.dismiss()
        val builder = AlertDialog.Builder(context!!)
        val secList = arrayOf("开启", "关闭")
        var switch = MMKV.defaultMMKV().getBoolean(SCConstant.PROXIMITY_SWITCH, true)
        val checkedItem: Int = if (switch) 0 else 1
        alertDialog = builder.setSingleChoiceItems(
            secList, checkedItem
        ) { _, which -> switch = which == 0 }
            .setPositiveButton("确定") { _, _ ->
                MMKV.defaultMMKV().putBoolean(SCConstant.PROXIMITY_SWITCH, switch)
                refresh()
            }
            .create().apply { show() }
    }


}