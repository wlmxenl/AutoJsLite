package org.autojs.autojs.ui.edit.toolbar;

import org.autojs.autojs.R;

import java.util.Arrays;
import java.util.List;

public class NormalToolbarFragment extends ToolbarFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_normal_toolbar;
    }

    @Override
    public List<Integer> getMenuItemIds() {
        return Arrays.asList(R.id.run, R.id.undo, R.id.redo, R.id.save);
    }
}
