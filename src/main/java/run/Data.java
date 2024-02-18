package run;

import model.basic.Result;

public class Data {
    private  Result result;
    public Data(Result result) {
        this.result = result;
    }
    public Data() {}

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
