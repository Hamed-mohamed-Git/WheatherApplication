package com.example.wheatherapplication.ui.common

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.DeleteConfermationBinding

class DeleteConfirmationFragmentDialog(
    private val deleteFragmentConfirmationListener: DeleteFragmentConfirmationListener
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = activity?.let {
        val builder = AlertDialog.Builder(context)
        builder.setView(
            DataBindingUtil.inflate<DeleteConfermationBinding>(
                layoutInflater,
                R.layout.delete_confermation,
                null,
                false
            ).apply {
                saveButton.setOnClickListener {
                    deleteFragmentConfirmationListener.onConfirmationDelete()
                    dismiss()
                }
                cancelButton.setOnClickListener {
                    dismiss()
                }

            }.root
        )
        builder.create().also {
            it.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 40))
        }
    } ?: Dialog(requireContext())


}