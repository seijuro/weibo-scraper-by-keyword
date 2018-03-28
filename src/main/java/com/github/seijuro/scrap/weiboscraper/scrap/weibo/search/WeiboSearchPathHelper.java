package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search;

public class WeiboSearchPathHelper {
    public static class TopMenu {
        public static String getLoginButtonElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.TopMenu.LOGIN_BUTTON_XPATH;
        }

        public static String getAccountButtonElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.TopMenu.ACCOUNT_BUTTON_XPATH;
        }
    }

    public static class Bottom {
        public static String getSearchInputBoxPath() {
            return WeiboSearchPathConstants.WeiboSearch.Bottom.SEARCH_INPUT_WRAP_XPATH;
        }
    }

    public static class Login {
        public static String getUsernameInputElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.LoginWindow.WINDOW_XPATH + WeiboSearchPathConstants.WeiboSearch.LoginWindow.USERNAME_XPATH;
        }

        public static String getPasswordInputElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.LoginWindow.WINDOW_XPATH + WeiboSearchPathConstants.WeiboSearch.LoginWindow.PASSWORD_XPATH;
        }

        public static String getSubmitButtonElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.LoginWindow.WINDOW_XPATH + WeiboSearchPathConstants.WeiboSearch.LoginWindow.SUBMIT_XPATH;
        }
    }

    public static class SearchForm {
        public static String getFormBoxPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_XPATH;
        }

        public static String getSearchBarPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_SEARCHBAR_XPATH;
        }

        public static String getSearchInputPath() {
            return getSearchBarPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_SEARCHBAR_INPUT_XPATH;
        }

        public static String getSearchButtonPath() {
            return getSearchBarPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_SEARCHBAR_BUTTON_XPATH;
        }

        public static String getFormBoxTabPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_XPATH
                    + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TAB_XPATH;
        }

        public static String getIntegratedElementPath() {
            return getFormBoxTabPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TABITEM_INTEGRATED_XPATH;
        }

        public static String getUserElementPath() {
            return getFormBoxTabPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TABITEM_USER_XPATH;
        }

        public static String getPageElementPath() {
            return getFormBoxTabPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TABITEM_PAGE_XPATH;
        }

        public static String getVideoElementPath() {
            return getFormBoxTabPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TABITEM_VIDEO_XPATH;
        }

        public static String getImageElementPath() {
            return getFormBoxTabPath() + WeiboSearchPathConstants.WeiboSearch.SearchPage.SEARCH_FORMBOX_TABITEM_IMAGE_XPATH;
        }
    }

    public static class SearchResult {
        public static String getWeiboDirectFeedElementPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_WEIBO_DIRECT_FEED_XPATH;
        }

        public static String getWeiboFeedListPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_FEEDLIST_XPATH;
        }

        public static String getWeiboExtraFeedListPath() {
            return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_EXTRA_FEEDLIST_XPATH;
        }

        public static class FeedListItem {
            public static String getItemPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_XPATH;
            }


            public static String getFaceLinkPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_DETAIL_FACE_LINK_XPATH;
            }

            public static String getFaceImagePath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_DETAIL_FACE_IMAGE_XPATH;
            }

            public static String getFromDatePath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_FROM_DATE_XPATH;
            }

            public static String getFromSourcePath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_FROM_SOURCE_XPATH;
            }

            public static String getContentComentPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_DETAIL_CONTENT_COMMENT_XPATH;
            }

            public static String getContentMediaPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_DETAIL_CONTENT_MEDIA_XPATH;
            }

            public static String getActionFavoritePath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_ACTION_XPATH;
            }

            public static String getActionForwardPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_ACTION_XPATH;
            }

            public static String getActionCommentPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_ACTION_XPATH;
            }

            public static String getActionLinkPath() {
                return WeiboSearchPathConstants.WeiboSearch.SearchResultPage.FeedListItem.FEEDLIST_ITEM_ACTION_XPATH;
            }
        }




        public static String getPagesWrapElementPath() {
            return getWeiboDirectFeedElementPath() + WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_PAGES_WRAP_XPATH;
        }

        public static String getPrevPageButtonElementPath() {
            return getPagesWrapElementPath() + WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_PAGES_PREVBUTTON_XPATH;
        }

        public static String getNextPageButtonElementPath() {
            return getPagesWrapElementPath() + WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_PAGES_NEXT_BUTTON_XPATH;
        }

        public static String getPagesListElementPath() {
            return getPagesWrapElementPath() + WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_PAGES_LIST_XPATH;
        }

        public static String getPageItemsElementPath() {
            return getPagesListElementPath() + WeiboSearchPathConstants.WeiboSearch.SearchResultPage.SEARCHRESULT_PAGES_LIST_ITEM_XPATH;

        }
    }
}
