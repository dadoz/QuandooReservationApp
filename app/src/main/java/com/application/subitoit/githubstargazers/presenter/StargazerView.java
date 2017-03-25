package com.application.subitoit.githubstargazers.presenter;

import java.util.List;

public interface StargazerView {
    void bindData(List<?> items, int i);
    void onRetrieveDataError(String error);
}
