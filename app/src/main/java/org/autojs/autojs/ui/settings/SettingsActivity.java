package org.autojs.autojs.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.stardust.theme.preference.ThemeColorPreferenceFragment;
import com.stardust.util.MapBuilder;

import org.autojs.autojs.R;
import org.autojs.autojs.ui.BaseActivity;

import java.util.Map;

import butterknife.ButterKnife;
import de.psdev.licensesdialog.LicenseResolver;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.License;

/**
 * Created by Stardust on 2017/2/2.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setUpUI();
    }

    void setUpUI() {
        setUpToolbar();
        getFragmentManager().beginTransaction().replace(R.id.fragment_setting, new PreferenceFragment()).commit();
    }

    private void setUpToolbar() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle(R.string.text_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static class PreferenceFragment extends ThemeColorPreferenceFragment {

        private Map<String, Runnable> ACTION_MAP;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onStart() {
            super.onStart();
            ACTION_MAP = new MapBuilder<String, Runnable>()
                    .put(getString(R.string.text_about_me_and_repo), () -> startActivity(new Intent(getActivity(), AboutActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)))
                    .put(getString(R.string.text_licenses), () -> showLicenseDialog())
                    .build();
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            Runnable action = ACTION_MAP.get(preference.getTitle().toString());
            if (action != null) {
                action.run();
                return true;
            } else {
                return super.onPreferenceTreeClick(preferenceScreen, preference);
            }
        }

        private void showLicenseDialog() {
            LicenseResolver.registerLicense(MozillaPublicLicense20.instance);
            new LicensesDialog.Builder(getActivity())
                    .setNotices(R.raw.licenses)
                    .setIncludeOwnLicense(true)
                    .build()
                    .showAppCompat();
        }

        public static class MozillaPublicLicense20 extends License {

            public static MozillaPublicLicense20 instance = new MozillaPublicLicense20();

            @Override
            public String getName() {
                return "Mozilla Public License 2.0";
            }

            @Override
            public String readSummaryTextFromResources(Context context) {
                return getContent(context, R.raw.mpl_20_summary);
            }

            @Override
            public String readFullTextFromResources(Context context) {
                return getContent(context, R.raw.mpl_20_full);
            }

            @Override
            public String getVersion() {
                return "2.0";
            }

            @Override
            public String getUrl() {
                return "https://www.mozilla.org/en-US/MPL/2.0/";
            }
        }

    }
}
