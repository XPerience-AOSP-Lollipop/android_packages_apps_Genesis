/*
 * Copyright (C) 2011-2017 The XPerience Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package mx.xperience.genesis.activities;

import android.annotation.Nullable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
//import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import mx.xperience.genesis.R;

import cyanogenmod.providers.CMSettings;

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SettingsFragment extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener {

        private static final String KEY_GENESIS_WARDEN_FORCE_STOP = "geneis_warden_force_stop";

        private SwitchPreference mForceStopPreference;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            mForceStopPreference = (SwitchPreference) findPreference(KEY_GENESIS_WARDEN_FORCE_STOP);
            mForceStopPreference.setOnPreferenceChangeListener(this);
            mForceStopPreference.setChecked(CMSettings.System.getInt(getContentResolver(), KEY_GENESIS_WARDEN_FORCE_STOP, 0) != 0);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final String key = preference.getKey();
            if (KEY_GENESIS_WARDEN_FORCE_STOP.equals(key)) {
                boolean value = (Boolean) newValue;
                CMSettings.System.putInt(getContentResolver(), KEY_GENESIS_WARDEN_FORCE_STOP, value ? 1 : 0);
            }
            return true;
        }
    }
}

