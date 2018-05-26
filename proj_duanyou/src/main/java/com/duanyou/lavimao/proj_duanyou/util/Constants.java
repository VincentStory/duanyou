package com.duanyou.lavimao.proj_duanyou.util;

/**
 * Created by luojialun on 2018/4/20.
 */

public class Constants {

    public static final String targetDyID = "targetDyID";

    public static final String beginContentID = "beginContentID";
    public static final String isEdit = "isEdit";

    public static final String type = "type";

    public static final String CLICK_BEAN = "clickBean";

    public static final String dyContextID = "dyContextID";

    public static final String DyContextsBean = "DyContextsBean";

    public static final String POSITION = "position";

    public static final String FILE_PATH = "file_path";

    public static final String CommentsNewBean = "CommentsNewBean";

    public static final String bitmap = "bitmap";

    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p>
     * <p>联通：130、131、132、145、155、156、166、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189、199</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(19[8,9])|(147)|(166))\\d{8}$";

    public static final int REQUEST_CODE_TAKE_VIDEO = 1;

    public static final int REQUEST_CODE_TAKE_PHOTO = 2;

    public static final int REQUEST_CODE_PICK_IMAGE = 3;

    public static final int REQUEST_CODE_PICK_VIDEO = 4;

    public static final int MY_PERMISSIONS_REQUEST_CALL_VIDEO = 1000;

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHOTO = 1001;

    public static final int MY_PERMISSIOS_REQUEST_SAVE_FILE = 1002;

}
