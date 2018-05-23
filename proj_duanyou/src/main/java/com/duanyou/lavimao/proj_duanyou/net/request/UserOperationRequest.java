package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

import java.util.List;

/**
 * Created by luojialun on 2018/4/21.
 */

public class UserOperationRequest extends BaseRequest {

    private List<Integer> dyDataID;
    private String type;
    private String operator;
    private String remark;

    public List<Integer> getDyDataID() {
        return dyDataID;
    }

    public void setDyDataID(List<Integer> dyDataID) {
        this.dyDataID = dyDataID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
