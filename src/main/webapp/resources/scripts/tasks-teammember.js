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
    var retrieveTasksServer = function (data, successCallback) {
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
        },
        /**
         * 111917kl
         * modification of the loadTasks method to load tasks retrieved from the server
         */
        loadServerTasks: function (tasks) {
            $(taskPage).find('#tblTasks tbody').empty();
            $.each(tasks, function (index, task) {
                if (!task.complete) {
                    task.complete = false;
                }
                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                taskCountChanged();
                console.log('about to render table with server tasks');
                renderTable(); // --skip for now, this just sets style class for overdue tasks 111917kl
            });
        },
        loadTasks: function () {
            $(taskPage).find("#tblTasks tbody").empty();
            retrieveTasksServer("", function (tasks) {
                tasks.sort(function (o1, o2) {
                    return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
                });
                $.each(tasks, function (index, task) {
                    if (!task.complete) {
                        task.complete = false;
                    }
                    $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                    taskCountChanged();
                    renderTable();
                });

            })
        },
        loadTeam: function () {
            var data = {
                id: $('#filterByTeamId').val(),
                method: "teamTask"
            };
            retrieveTasksServer(data,
                function(tasks) {
                    tasksController.loadServerTasks(tasks);
                });
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
        // loadTasks : function() {
        //    // $(taskPage).find('#tblTasks tbody').empty();
        //    // $.each(tasks, function (index, task) {
        //    //     if (!task.complete) {
        //    //         task.complete = false;
        //    //     }
        //    //     $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
        //    //     taskCountChanged();
        //    //     console.log('about to render table with server tasks');
        //    //     renderTable(); // --skip for now, this just sets style class for overdue tasks 111917kl
        //    // });
        // 	$(taskPage).find('#tblTasks tbody').empty();
        // 	retrieveTasksServer("", function(tasks) {
        // 	    console.log("TASKS FROM THE SERVER");
        //            tasks.sort(function (o1, o2) {
        //                return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
        //            });
        //            $.each(tasks, function (index, task) {
        //                if (!task.complete) {
        //                    task.complete = false;
        //                }
        //                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
        //                taskCountChanged();
        //                renderTable();
        //            });
        //        });
        // 	// storageEngine.findAll('task', function(tasks) {
        // 	// 	tasks.sort(function(o1, o2) {
        // 	// 		return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
        // 	// 	});
        // 	// 	$.each(tasks, function(index, task) {
        // 	// 		if (!task.complete) {
        // 	// 			task.complete = false;
        // 	// 		}
        // 	// 		$('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
        // 	// 		taskCountChanged();
        // 	// 		renderTable();
        // 	// 	});
        // 	// }, errorLogger);
        // },
        loadUsers: function () {
            storageEngine.findAll('user', function (users) {
                console.log("user",users);
                console.log("users",users);
                $.each(users, function (index, user) {
                    $("#taskForm select").append($("<option>").attr("value", user.id).text(user.id + " - " + user.userName));
                });
            }, errorLogger);
        },

    }
}();
