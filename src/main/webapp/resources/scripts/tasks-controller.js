tasksController = function() { 
	
	function errorLogger(errorCode, errorMessage) {
	    console.log(errorCode, errorMessage);
		console.log(errorCode +':'+ errorMessage);
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
    var retrieveTasksServer = function(data, successCallback) {
        $.ajax("TaskServlet", {
            "type": "get",
            dataType: "json",
			data: data
        }).success(successCallback)
			.fail(function(err) {
				console.log("ERRORO ON jsp ", err);
			}); //need reference to the tasksController object
    };
    var retrieveTaskById = function(id, successCallback) {
    	$.ajax("TaskServlet", {
    		"type": "post",
			dataType: "json",
			data: {
    			id: id
			}
		}).success(successCallback)
			.fail(errorLogger);
	};
    var fetchUser = function(data, successCallback) {
        $.ajax("UserServlet", {
            "type": "get",
            dataType: "json",
            data: data
        }).success(successCallback)
            .fail(function(err) {
                console.log("ERRORO ON jsp ", err);
            }); //need reference to the tasksController object
    };

    /**
	 * 111917kl
	 * callback for retrieveTasksServer
     * @param data
     */
    function displayTasksServer(data) { //this needs to be bound to the tasksController -- used bind in retrieveTasksServer 111917kl
    	console.log("displayTasksServer", data);
        tasksController.loadServerTasks(data);
    }
	
	function taskCountChanged() {
		var count = $(taskPage).find( '#tblTasks tbody tr').length;
		$('footer').find('#taskCount').text(count);
	}
	
	function clearTask() {
		$(taskPage).find('#taskForm').fromObject({});
	}
	
	function renderTable() {
		$.each($(taskPage).find('#tblTasks tbody tr'), function(idx, row) {
			var due = Date.parse($(row).find('[datetime]').text());
			if (due.compareTo(Date.today()) < 0) {
				$(row).addClass("overdue");
			} else if (due.compareTo((2).days().fromNow()) <= 0) {
				$(row).addClass("warning");
			}
		});
	}
	
	return { 
		init : function(page, callback) { 
			if (initialised) {
				callback()
			} else {
				taskPage = page;
				// storageEngine.init(function() {
				// 	storageEngine.initObjectStore('task', function() {
				// 		callback();
				// 	}, errorLogger)
				// }, errorLogger);
                storageEngine.init(function() {
                    storageEngine.initObjectStore('user', function() {
                        callback();
                    }, errorLogger)
                }, errorLogger);
                $(taskPage).find('[required="required"]').prev('label').append( '<span>*</span>').children( 'span').addClass('required');
				$(taskPage).find('tbody tr:even').addClass('even');
				
				$(taskPage).find('#btnAddTask').click(function(evt) {
					evt.preventDefault();
					fetchUser("", tasksController.loadUsers);
					$(taskPage).find('#taskCreation').removeClass('not');
				});


                $(taskPage).find('#btnAddUser').click(function(evt) {
                    evt.preventDefault();
                    $(taskPage).find('#userCreation').removeClass('not');
                });

                /**	 * 11/19/17kl        */
                $(taskPage).find('#btnRetrieveTasks').click(function(evt) {
                    evt.preventDefault();
                    console.log('making ajax call');
                    retrieveTasksServer("",function(data) {
                        tasksController.loadServerTasks(data);
					});
                });
				
				$(taskPage).find('#tblTasks tbody').on('click', 'tr', function(evt) {
					$(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
				});	
				
				$(taskPage).find('#tblTasks tbody').on('click', '.deleteRow', 
					function(evt) {
						var data = {
							id: $(evt.target).data().taskId,
							method: "delete"
						};
                        retrieveTasksServer(data,
                            function() {
                                $(evt.target).parents('tr').remove();
                                taskCountChanged();
                            });

						// storageEngine.delete('task', $(evt.target).data().taskId,
						// 	function() {
						// 		$(evt.target).parents('tr').remove();
						// 		taskCountChanged();
						// 	}, errorLogger);
						
					}
				);
				
				$(taskPage).find('#tblTasks tbody').on('click', '.editRow', 
					function(evt) {
						var data = {
							id: $(evt.target).data().taskId,
							method: "getTask"
						};
						$(taskPage).find('#taskCreation').removeClass('not');

                        retrieveTasksServer(data, function(task) {
                        console.log("TASK in edit row", task);
                            // fetchUser("", function (users, tasks) {
                        	 //    tasksController.loadUsers(users);
                            // });
                            $(taskPage).find('#taskForm').fromObject(task);
						});


                        // retrieveTaskById(data, function(task) {
                        //     $(taskPage).find('#taskForm').fromObject(task);
                        // });
						// storageEngine.findById('task', $(evt.target).data().taskId, function(task) {
						// 	$(taskPage).find('#taskForm').fromObject(task);
						// }, errorLogger);
					}
				);
				
				$(taskPage).find('#clearTask').click(function(evt) {
					evt.preventDefault();
					clearTask();
				});
				
				$(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function(evt) {
                    var task = {
                    	id: $(evt.target).data().taskId,
						method: "complete"
					};
					retrieveTasksServer(task, function (tasks) {
                        tasksController.loadServerTasks(tasks);
                    });
                    // retrieveTaskById($(evt.target).data().taskId, function(task) {
                    //     task.complete = true;
                    //     task.method = "complete";
                    //     console.log("FROOOOOOOM" , task);
						// retrieveTasksServer(task, function () {
                    //         tasksController.loadTasks();
                    //     });
                    //
                    // });
					// storageEngine.findById('task', $(evt.target).data().taskId, function(task) {
					// 	task.complete = true;
                    //
                    //
					// 	storageEngine.save('task', task, function() {
					// 		tasksController.loadTasks();
					// 	},errorLogger);
					// }, errorLogger);
				});
				
				$(taskPage).find('#saveTask').click(function(evt) {
					evt.preventDefault();
					if ($(taskPage).find('#taskForm').valid()) {
						console.log("before toObject", $(taskPage).find('#taskForm'));
						var task = $(taskPage).find('#taskForm').toObject();
						console.log(task);
                        if(task.id !== "") {
                            task.method = "edit";
						}
						else {
                            task.method="add";
						}
                        retrieveTasksServer(task, function(tasks) {
                            $(taskPage).find('#tblTasks tbody').empty();
                            tasksController.loadServerTasks(tasks);
                            clearTask();
                            $(taskPage).find('#taskCreation').addClass('not');
						});
						// storageEngine.save('task', task, function() {
                        //
						// 	$(taskPage).find('#tblTasks tbody').empty();
						// 	tasksController.loadTasks();
						// 	clearTask();
						// 	$(taskPage).find('#taskCreation').addClass('not');
						// }, errorLogger);
					}
				});

				$(taskPage).find('#saveUser').click(function (evt) {
					evt.preventDefault();
					if ($(taskPage).find('#userForm').valid()) {
                        var user = $(taskPage).find('#userForm').toObject();
                        user.method = "add";
                        fetchUser(user, function(users) {
                            tasksController.loadUsers(users);
                            $(taskPage).find('#userForm').fromObject({});
                            $(taskPage).find('#userCreation').addClass('not');
                        });
                        // storageEngine.save('user', user, function() {
                        //     alert("is called");
                        //     tasksController.loadUsers();
                        //     $(taskPage).find('#userForm').fromObject({});
                        //     $(taskPage).find('#userCreation').addClass('not');
                        // }, errorLogger);
					}
                });

                $("#sortDue").click(function (evt) {
                    console.log("sortDue");
                    console.log($(taskPage).find('#tblTasks tbody tr'));
                    var data = {
                        sortType: "dueDate"
                    }
                    retrieveTasksServer(data, function(tasks) {
                        $(taskPage).find('#tblTasks tbody').empty();
                        tasksController.loadServerTasks(tasks);
                    });
                });

                $("#sortPriority").click(function (evt) {
                    console.log($(taskPage).find('#tblTasks tbody tr'));
                    var data = {
                        sortType: "priority"
                    }
                    retrieveTasksServer(data, function(tasks) {
                        $(taskPage).find('#tblTasks tbody').empty();
                        tasksController.loadServerTasks(tasks);
                    });
                });

            	initialised = true;
			}
		},
        /**
		 * 111917kl
		 * modification of the loadTasks method to load tasks retrieved from the server
         */
		loadServerTasks: function(tasks) {
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
		loadTasks: function() {
			$(taskPage).find("#tblTasks tbody").empty();
			retrieveTeamServlet("", function (tasks) {
                    tasks.sort(function(o1, o2) {
                        return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
                    });
                    $.each(tasks, function(index, task) {
                        if (!task.complete) {
                            task.complete = false;
                        }
                        $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                        taskCountChanged();
                        renderTable();
                    });

			})
		},
        loadInitUser: function() {
			fetchUser("", function(users) {
				tasksController.loadUsers(users);
			});

		},
		loadUsers: function (users) {
			$("#taskForm #userSelect").empty();
            $.each(users, function(index, user) {
                $("#taskForm #userSelect").append($("<option>").attr("value",user.id).text(user.id + " - " +user.userName));
            });
        }

	} 
}();
