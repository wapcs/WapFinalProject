teamController = function () {

    function errorLogger(errorCode, errorMessage) {
        console.log(errorCode + ':' + errorMessage);
    }

    var taskPage;
    var initialised = false;

    /**
     * makes json call to server to get task list.
     * currently just testing this and writing return value out to console
     * 111917kl
     */
        // function retrieveTasksServer() {
        //     $.ajax("TaskServlet", {
        //         "type": "get",
        // 	dataType: "json"
        //         // "data": {
        //         //     "first": first,
        //         //     "last": last
        //         // }
        //     }).done(displayTasksServer.bind()); //need reference to the tasksController object
        // }
    var retrieveTeamServlet = function (data, successCallback) {
            $.ajax("TeamServlet", {
                "type": "get",
                dataType: "json",
                data: data

            }).success(successCallback)
                .fail(function (err) {
                    console.log("ERRORO ON jsp ", err);
                }); //need reference to the tasksController object
        };
    var retrieveTeamById = function (id, successCallback) {
        $.ajax("TeamServlet", {
            "type": "post",
            dataType: "json",
            data: {
                id: id
            }
        }).success(successCallback)
            .fail(errorLogger)
    };
    /**
     * 111917kl
     * callback for retrieveTasksServer
     * @param data
     */

    function renderTable() {
        $.each($(taskPage).find('#tblTasks tbody tr'), function (idx, row) {
            var due = Date.parse($(row).find('[datetime]').text());
            if (due.compareTo(Date.today()) < 0) {
                $(row).addClass("overdue");
            } else if (due.compareTo((2).days().fromNow()) <= 0) {
                $(row).addClass("warning");
            }
        });
    }

    return {
        init: function (page, callback) {
            $('#filterByTeamId').bind("change", teamController.loadTasks());
            $('#filterByTeamId').click(function() {
                retrieveTeamServlet("", function(tasks) {
                    tasksController.loadServerTasks(tasks);
                })
            })
        },
        loadTeam: function () {
            var data = {
                // id: $(evt.target).data().taskId,
                method: ""
            };
            retrieveTasksServer(data,
                function(teams) {
                    $('#filterByTeamId').append( $("<option>").attr("value", 0).html("Choose team") );
                    $.each(teams, function(i, v) {
                        $('#filterByTeamId').append( $("<option>").attr("value", v.id).html(v.teamName) );
                    });
                });
        },

    }
}();
