package com.yks.simpledemo2.tools;

/**
 * 描述：基础数据解析
 * 作者：zzh
 * time:2018/08/28
 */

public class BaseHttpBean<H> {
    /**
     * 取值：1（操作成功），0（操作失败）
     */
    private String resultType;
    /**
     * 返回结果码(参考文档最开始部分的结果吗)，100000为成功，其余都为失败
     */
    private String resultCode;
    /**
     * 返回的描述
     */
    private String resultDesc;
    /**
     * 结果对象
     */
    private H result;

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public H getResult() {
        return result;
    }

    public void setResult(H result) {
        this.result = result;
    }


    public boolean isOK() {
        return "100000".equals(resultCode);
    }

    @Override
    public String toString() {
        return "BaseHttpBean{" +
                "resultType='" + resultType + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", result=" + result +
                '}';
    }
}
