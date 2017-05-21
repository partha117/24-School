/**
 * Created by ASUS on 5/1/2017.
 */


$(document).ready(function () {
    $("#notice").on("click", function (e) {
        e.preventDefault();
        $(this).addClass("hidden");
        $("#notice-form").removeClass("hidden");
        if( !$("#video-form").hasClass("hidden") ){
            $("#video-form").addClass("hidden");
            $("#video").removeClass("hidden");
        }
    });
    $("#video").on("click", function (e) {
        e.preventDefault();
        $(this).addClass("hidden");
        $("#video-form").removeClass("hidden");
        if( !$("#notice-form").hasClass("hidden") ){
            $("#notice-form").addClass("hidden");
            $("#notice").removeClass("hidden");
        }
    });


});