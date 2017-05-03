/**
 * Created by ASUS on 01-May-17.
 */
function createPost(path, param) {


    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.getElementById("form");
    document.write("done");






            var hiddenField = document.createElement("input");
            //hiddenField.setAttribute("type", "hidden");

    try {
        hiddenField.setAttribute("selects",param);
    }
    catch(err) {
        document.write(err.message);
    }
            document.write("param added")
    try {
        form.appendChild(hiddenField);
    }
    catch(err) {
        document.write(err.message);
    }
            document.write("appended");
            form.submit();
            document.write("submitted");





}
function createMessage(par,par1) {
    document.write(par1)

}
