package com.example.wheatherapplication.ui.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.DeleteConfermationBinding
import com.example.wheatherapplication.ui.common.navigation.DialogEvent
import com.example.wheatherapplication.ui.common.navigation.NavGraphViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteConfirmationFragmentDialog() : DialogFragment() {
    private val navViewModel: NavGraphViewModel by hiltNavGraphViewModels(R.id.nav_graph)

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
                    navViewModel.onEvent(DialogEvent.OnDeleted)
                    dismiss()
                }
                cancelButton.setOnClickListener {
                    navViewModel.onEvent(DialogEvent.OnCancel)
                    dismiss()
                }

            }.root
        )
        builder.create().also {
            it.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 40))
        }
    } ?: Dialog(requireContext())


}