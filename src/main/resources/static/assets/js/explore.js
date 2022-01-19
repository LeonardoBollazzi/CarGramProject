function getExploreMedia(callback) {
    $.ajax({
        type: "GET",
        dataType: "",
        url: serviceEndpointURL + "/api/allMediaHandling/",
        success: function (data) {
            callback(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            callback(0);
        }
    });
}