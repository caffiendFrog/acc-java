package org.catalyst.gwt.widgets;

import com.google.gwt.user.client.ui.ListBox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiSelectListBox extends ListBox {
    public List<Integer> getSelectedIds() {
        final List<Integer> results = IntStream.range(0, getItemCount())
                .filter(index -> isItemSelected(index))
                .mapToObj(index -> Integer.parseInt(getValue(index)))
                .collect(Collectors.toList());
        return results;
    }
}
