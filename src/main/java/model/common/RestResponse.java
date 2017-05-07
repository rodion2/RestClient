package model.common;

import model.WinApiClass;
import model.WinApiFunction;
import model.WinApiParameter;

/**
 * Created by rodya on 7.5.17.
 */
public class RestResponse {
    private WinApiClass winApiClass;
    private WinApiParameter winApiParameter;
    private WinApiFunction winApiFunction;

    public WinApiFunction getWinApiFunction() {
        return winApiFunction;
    }

    public void setWinApiFunction(WinApiFunction winApiFunction) {
        this.winApiFunction = winApiFunction;
    }

    public WinApiParameter getWinApiParameter() {
        return winApiParameter;
    }

    public void setWinApiParameter(WinApiParameter winApiParameter) {
        this.winApiParameter = winApiParameter;
    }

    public WinApiClass getWinApiClass() {
        return winApiClass;
    }

    public void setWinApiClass(WinApiClass winApiClass) {
        this.winApiClass = winApiClass;
    }
}
