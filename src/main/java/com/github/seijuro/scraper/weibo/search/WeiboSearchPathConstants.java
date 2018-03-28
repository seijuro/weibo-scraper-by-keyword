package com.github.seijuro.scraper.weibo.search;

public class WeiboSearchPathConstants {
    public static class WeiboSearch {
        public static class TopMenu {
            public static final String LOGIN_BUTTON_XPATH = "//div[contains(@class, 'gn_login')]/ul[contains(@class, 'gn_login_list')]/li/a[@node-type='loginBtn']";
            public static final String ACCOUNT_BUTTON_XPATH = "//div[contains(@class, 'gn_set')]/div[@class='gn_set_list']/a[@node-type='account' and @nm='account']";
        }

        public static class Bottom {
            public static final String SEARCH_INPUT_WRAP_XPATH = "//div[@class='bottom_wrap']/div[contains(@class, 'bottom_search')]/div[@class='search_input']/div[@class='search_input_wrap']";
        }

        public static class Footer {
            public static final String GLOBAL_FOOTER_XPATH = "//div[contains(@class, 'search_footer')]/div[@class='global_footer']";
            public static final String HELP_LINK_BOX_XPATH = "//div[@class='help_link']";
        }

        public static class LoginWindow {
            public static final String WINDOW_XPATH = "//div[contains(@class, 'layer_login_register_v2') and @node-type='inner']//div[contains(@class, 'form_login_register') and @node-type='login_frame']";
            public static final String USERNAME_XPATH = "//div[contains(@class, 'item') and contains(@class, 'username') and @node-type='username_box']/input[@node-type='username' and @name='username' and @type='text']";
            public static final String PASSWORD_XPATH = "//div[contains(@class, 'item') and contains(@class, 'password') and @node-type='password_box']/input[@node-type='password' and @name='password' and @type='password']";
            public static final String SUBMIT_XPATH = "//a[@node-type='submitBtn' and @action-type='btn_submit']";
        }

        public static class SearchPage {
            public static final String SEARCH_SEARCHBAR_XPATH = "//div[contains(@class, 'search_input') and @node-type='searchBar']";
            public static final String SEARCH_SEARCHBAR_INPUT_XPATH = "//div[contains(@class, 'searchInp_box')]/div[@class='searchInp_auto']/input[@class='searchInp_form' and @node-type='text']";
            public static final String SEARCH_SEARCHBAR_BUTTON_XPATH = "//div[contains(@class, 'searchBtn_box')]/a[@class='searchBtn' and @node-type='submit']";

            public static final String SEARCH_FORMBOX_XPATH = "//div[@class='S_main']//div[@class='search_head_formbox']";
            public static final String SEARCH_FORMBOX_TAB_XPATH = "//ul[contains(@class, 'formbox_tab') and @node-type='searchItems']";
            public static final String SEARCH_FORMBOX_TABITEM_INTEGRATED_XPATH = "//a[@action-type='type' and contains(@action-data, 'weibo') and contains(@suda-data, 'index_weibo')]";
            public static final String SEARCH_FORMBOX_TABITEM_USER_XPATH = "//a[@action-type='type' and contains(@action-data, 'user') and contains(@suda-data, 'index_user')]";
            public static final String SEARCH_FORMBOX_TABITEM_PAGE_XPATH = "//a[@action-type='type' and contains(@action-data, 'list/relpage') and contains(@suda-data, 'index_list_relpage')]";
            public static final String SEARCH_FORMBOX_TABITEM_VIDEO_XPATH = "//a[@action-type='type' and contains(@action-data, 'video') and contains(@suda-data, 'index_weibo')]";
            public static final String SEARCH_FORMBOX_TABITEM_IMAGE_XPATH = "//a[@action-type='type' and contains(@action-data, 'pic') and contains(@suda-data, 'index_pic')]";
        }

        public static class SearchResultPage {
            public static final String SEARCHRESULT_WEIBO_DIRECT_FEED_XPATH = "//div[contains(@class, 'S_main')]//div[@id='plc_main']//div[contains(@class, 'S_content')]//div[@id='pl_weibo_direct']/div[@class='search_feed']";

            public static final String SEARCHRESULT_FEEDLIST_XPATH = SEARCHRESULT_WEIBO_DIRECT_FEED_XPATH + "/div[contains(@class, 'feed_list') and not(contains(@class, 'W_texta')) and @node-type='feed_list']";
            public static final String SEARCHRESULT_EXTRA_FEEDLIST_XPATH = SEARCHRESULT_WEIBO_DIRECT_FEED_XPATH + "/div[contains(@class, 'feed_lists') and contains(@class, 'W_texta') and @node-type='feed_list']";

            public static class FeedListItem {
                public static final String FEEDLIST_ITEM_XPATH = "div[contains(@class, 'WB_cardwrap')]/div[@action-type='feed_list_item']";

                public static final String FEEDLIST_ITEM_DETAIL_XPATH = "//div[contains(@class, 'feed_list')]";
                public static final String FEEDLIST_ITEM_ACTION_XPATH = "//div[contains(@class, 'feed_action')]";

                //  detail(s)
                public static final String FEEDLIST_ITEM_DETAIL_FACE_XPATH = FEEDLIST_ITEM_DETAIL_XPATH + "//div[@class='face']";
//                public static final String FEEDLIST_ITEM_DETAIL_CONTENT_XPATH = FEEDLIST_ITEM_DETAIL_XPATH + "//div[contains(@class, 'content')]/div[contains[@class, 'feed_content')]";

                //  detail - face
                public static final String FEEDLIST_ITEM_DETAIL_FACE_LINK_XPATH = FEEDLIST_ITEM_DETAIL_FACE_XPATH + "/a";
                public static final String FEEDLIST_ITEM_DETAIL_FACE_IMAGE_XPATH = FEEDLIST_ITEM_DETAIL_FACE_XPATH + "/a/img";

                //  detail - content
                public static final String FEEDLIST_ITEM_DETAIL_CONTENT_COMMENT_XPATH = FEEDLIST_ITEM_DETAIL_XPATH + "//p[@class='comment_txt' and @node-type='feed_list_content']";
                public static final String FEEDLIST_ITEM_DETAIL_CONTENT_MEDIA_XPATH = FEEDLIST_ITEM_DETAIL_XPATH + "//div[contains(@class, 'WB_media_wrap')]/div[@class='media_box']";

                //  detail - content - from
                public static final String FEEDLIST_ITEM_DETAIL_CONTENT_FROM_XPATH = FEEDLIST_ITEM_DETAIL_XPATH + "//div[contains(@class, 'content')]/div[contains(@class, 'feed_from')]";
                public static final String FEEDLIST_ITEM_FROM_DATE_XPATH = FEEDLIST_ITEM_DETAIL_CONTENT_FROM_XPATH + "/a[@node-type='feed_list_item_date']";
                public static final String FEEDLIST_ITEM_FROM_SOURCE_XPATH = FEEDLIST_ITEM_DETAIL_CONTENT_FROM_XPATH + "/a[@rel='nofollow']";

                //  action(s)
                public static final String FEEDLIST_ITEM_ACTION_ITEMS_XPATH = FEEDLIST_ITEM_ACTION_XPATH + "/ul[contains(@class, 'feed_action_info')]/li/a";
                public static final String FEEDLIST_ITEM_ACTION_ITEM_FAVORITE_XPATH = FEEDLIST_ITEM_ACTION_XPATH + "/ul[contains(@class, 'feed_action_info')]/li/a[@action-type='feed_list_favorite']";
                public static final String FEEDLIST_ITEM_ACTION_ITEM_FORWARD_XPATH = FEEDLIST_ITEM_ACTION_XPATH + "/ul[contains(@class, 'feed_action_info')]/li/a[@action-type='feed_list_forward']";
                public static final String FEEDLIST_ITEM_ACTION_ITEM_COMMENT_XPATH = FEEDLIST_ITEM_ACTION_XPATH + "/ul[contains(@class, 'feed_action_info')]/li/a[@action-type='feed_list_comment']";
                public static final String FEEDLIST_ITEM_ACTION_ITEM_LIKE_XPATH = FEEDLIST_ITEM_ACTION_XPATH + "/ul[contains(@class, 'feed_action_info')]/li/a[@action-type='feed_list_like']";
            }

            public static final String SEARCHRESULT_PAGES_WRAP_XPATH = "//div[@class='W_pages']";
            public static final String SEARCHRESULT_PAGES_PREVBUTTON_XPATH = "//a[contains(@class, 'page') and  contains(@class, 'prev')]";
            public static final String SEARCHRESULT_PAGES_NEXT_BUTTON_XPATH = "//a[contains(@class, 'page') and  contains(@class, 'next')]";
            public static final String SEARCHRESULT_PAGES_LIST_XPATH = "//span[@class='list']";
            public static final String SEARCHRESULT_PAGES_LIST_ITEM_XPATH = "//div[contains(@class, 'layer_menu_list') and @action-type='feed_list_page_morelist']/ul/li";
        }
    }
}
