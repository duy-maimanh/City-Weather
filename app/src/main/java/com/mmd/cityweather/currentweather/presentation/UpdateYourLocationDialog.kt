package com.mmd.cityweather.currentweather.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdateYourLocationDialog(private val yes: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Phát hiện vị trí mới!")
            .setMessage(
                "Chúng tôi thấy bạn đang ở một nơi khác với vị trí " +
                        "được lưu trữ gần nhất. Bạn có muốn thay đổi vị trí mặc " +
                        "định không?\n\n" +
                        "Quan trọng: Nếu bạn chọn *Luôn đồng ý*, ứng dụng sẽ " +
                        "tự động thay đổi vị trí khi vị trí hiện tại khác với" +
                        " vị trí mặc định. Bạn có thể tắt tính năng này trong" +
                        " phần cài đặt."
            )
            .setPositiveButton("Luôn đồng ý") { _, _ ->
                yes.invoke()
            }
            .setNegativeButton("Đồng ý") { _, _ -> }
            .setNeutralButton("Không đồng ý") { _, _ -> }
            .create()
    }
}
