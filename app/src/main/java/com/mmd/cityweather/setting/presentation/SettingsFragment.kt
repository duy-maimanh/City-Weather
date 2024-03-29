/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.setting.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.mmd.cityweather.R
import com.mmd.cityweather.privacy.presentation.PrivacyPopupFragment

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var toolbar: Toolbar

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)

        val preApproveToUseLocation =
            preferenceScreen.findPreference<SwitchPreferenceCompat>("is_user_approve_location")
        preApproveToUseLocation?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true) {
                parentFragmentManager.setFragmentResultListener(
                    PrivacyPopupFragment.PRIVACY_KEY,
                    this
                ) { _, bundle ->
                    val result =
                        bundle.getBoolean(PrivacyPopupFragment.APPROVE_LOCATION_KEY)
                    if (result) {
                        preApproveToUseLocation.isChecked = true
                    }
                }
                findNavController().navigate(R.id.action_settingsFragment_to_privacyPopupFragment)
            } else {
                preApproveToUseLocation.isChecked = false
            }
            return@setOnPreferenceChangeListener false
        }
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (toolbar.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin =
            statusBarHeight

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
