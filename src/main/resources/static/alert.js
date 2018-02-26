var alertTool = angular.module('alertTool', ['ngRoute']);

alertTool.config(function ($routeProvider){
    $routeProvider

    .when('/', {
        templateUrl: 'alertList.html',
        controller: 'alertRoute'
     })

    .when('/conversation/:alertId', {
             templateUrl: 'conversation.html',
             controller: 'conversationRoute'
     })

	.when('/messageList/:conversationId', {
		templateUrl: 'messageList.html',
		controller: 'messageListRoute'
	})

    .when('/message/:messageId', {
             templateUrl: 'viewMessage.html',
             controller: 'viewMessageRoute'
     })

    .when('/newMessage/:alertId', {
            templateUrl: 'newMessage.html',
            controller: 'newMessageRoute'
     })

    .when('/storedTemplates', {
        templateUrl: 'storedTemplates.html'
    })

     .when('/createTemplate', {
        templateUrl: 'createTemplate.html'
     })

    /*
     .when('/storedTemplates/:templateTitle', {
            templateUrl: 'pages/template.html',
            controller: 'templateRoute'
    })
    */

});

alertTool.factory('Scopes', function ($rootScope) {
    var mem = {};

    return {
        store: function (key, value) {
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        }
    };
});

alertTool.controller('alertRoute', function($scope){
});

alertTool.controller('newMessageRoute', function($scope, $routeParams, Scopes){
   Scopes.store('newMessageRoute', $scope);
   $scope.alertId = $routeParams.alertId;
});

alertTool.controller('conversationRoute', function($scope, $routeParams, Scopes){
    Scopes.store('conversationRoute', $scope);
    $scope.alertId = $routeParams.alertId;
});

alertTool.controller('messageListRoute', function($scope, $routeParams, Scopes){
    Scopes.store('messageListRoute', $scope);
    $scope.conversationId = $routeParams.conversationId;
});

alertTool.controller('viewMessageRoute', function($scope, $routeParams, Scopes){
	Scopes.store('viewMessageRoute', $scope);
    $scope.messageId = $routeParams.messageId;
});

/*
alertTool.controller('templateRoute', function($scope, $routeParams, Scopes){
    Scopes.store('templateRoute', $scope);
    $scope.templateTitle = $routeParams.templateTitle;
*/

alertTool.controller('alertsCtrl', function($scope, $http, Scopes){
	Scopes.store('alertsCtrl', $scope);

    // Dummy data from URL
     $http.get("http://localhost:8080/alert/fullListAlertsDummy").then(function(response) {
            $scope.alerts = response.data;

        });

    //// Actual data from DB - wont work on citi
    // $http.get("/alert/fullListActual").then(function(response) {
    //        $scope.alerts = response.data;
    //    });

    $scope.selId = -1;
    $scope.selAlert = function (alert, id){
       $scope.selectedAlert=alert;
       $scope.selId=id;

       var alertHref='#/conversation/'+$scope.selectedAlert.id;
       document.getElementById("viewRfiHref").href=alertHref;
       document.getElementById("viewRfiHref").classList.remove('greyOut');
       document.getElementById("viewRfiHref").classList.add('enable');

       var rfiHref='#/newMessage/'+$scope.selectedAlert.id;
       document.getElementById("newRfiHref").href=rfiHref;
       document.getElementById("newRfiHref").classList.remove('greyOut');
       document.getElementById("newRfiHref").classList.add('enable');
        }

    $scope.isSelAlert=function(alert){
        return $scope.selAlert===alert;
    }

	document.getElementById("searcher").addEventListener("keyup", searchAlerts);

	function searchAlerts() {
		console.log("Getting to searchies");
		var input, filter, table, tr, td, i;
		input = document.getElementById("searcher");
		filter = input.value.toUpperCase();
		table = document.getElementById("alertTable");
		tr = table.getElementsByTagName("tr");
			
		for (i=0; 0<tr.length; i++){
			td = tr[i].getElementsByTagName("td")[1];
			if (td){
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1){
					tr[i].style.display="";
				} else {
					tr[i].style.display="none";
				}
			}
		}
	}
	
});

alertTool.controller('conversationCtrl', function($scope, $http, Scopes){
	Scopes.store('conversationCtrl', $scope);
	
 	$http.get("http://localhost:8080/conversation/conversationListFullDummy").then(function(response) {
        $scope.conversations = response.data;
 	}).finally(function(messages){
    $scope.filtConversations = $scope.conversations.filter(function (conversation){
            return conversation.alertId===Scopes.get('conversationRoute').alertId;
    	});
	});

	//// Actual data from DB
	//   $http.get("/rfi/fullListActual").then(function(response) {
	//           $scope.messages = response.data;
	//       });


    $scope.selId = -1;
    $scope.selConversation = function (conversation, id){
       $scope.selectedConversation=conversation;
       $scope.selId=id;
       var messageHref='#/messageList/'+$scope.selectedConversation.conversationId;
       document.getElementById("viewMessageListHref").href=messageHref;
       document.getElementById("viewMessageListHref").classList.remove('greyOut');
       document.getElementById("viewMessageListHref").classList.add('enable');
    }
			
    $scope.isSelConversation=function(conversation){
        return $scope.selectedConversation===conversation;
    }

    $scope.orderByMe=function(x){       
        $scope.myOrderBy=x;
    }

	$scope.updateConversation=function(conversation, id){

        document.getElementById("updateConvo").classList.remove('greyOut');
        document.getElementById("updateConvo").classList.add('enable');
        $http.post("http://localhost:8080/conversation/changeConversation/" + $scope.selectedConversation.conversationId).then(function(response){
        $scope.conversations.reload()},

        function(response){
        alert("Something didn't work!")});

	}
});

alertTool.controller('newMessageCtrl', function($scope, $http, Scopes){
	var pdfGenerated = false;
	var alertId = Scopes.get('newMessageRoute').alertId;
	var pdf = new jsPDF();
	
	// this way of getting the alert description is janky, better to retrieve from database: used for dev/test purposes only
	var messageAlert = Scopes.get('alertsCtrl').selectedAlert;
	
	//$http.get("http://localhost:8080/alert/getAlertById/"+alertId).then(function(response) {
	//	var messageAlert = response.data;
	//});
	
	var attachments = document.getElementById("attachments");
	if ('files' in attachments){
		if (attachments.files.length > 0) {
			console.log('Files uploaded');
			document.getElementById('processedAttachments').files = attachments;
		}
	}
	
	var alertDesc = messageAlert.desc;
	document.getElementById('attachPDF').onclick = function() {
		if(this.checked){
			if(!pdfGenerated){
				console.log('Generating PDF');
				pdf.text(alertDesc, 10, 10);
				var pdfName = "alert"+alertId+".pdf";
				pdfGenerated=true;
				var pdfBytestream = btoa(pdf.output);
				console.log(pdfBytestream);
				document.getElementById('generatedPDF').files = pdfFile;
				console.log(document.getElementById('generatedPDF'));
			} else {
				console.log('PDF exists - attaching');
			}
		} else {
			console.log('PDF not being attached');
		}
	}
	
	var pdfGenerated = false;
	var alertId = Scopes.get('newMessageRoute').alertId;
	
	// this way of getting the alert description is bad and nasty
	var messageAlert = Scopes.get('alertsCtrl').selectedAlert;
	
	//$http.get("http://localhost:8080/alert/getAlertById/"+alertId).then(function(response) {
	//	var messageAlert = response.data;
	//});
	
	var attachments = document.getElementById("attachments");
	if ('files' in attachments){
		if (attachments.files.length > 0) {
			document.getElementById('processedAttachments').files = attachments;
		}
	}
	
	var alertDesc = messageAlert.desc;
	document.getElementById('attachPDF').onclick = function() {
		if(this.checked){
			if(!pdfGenerated){
				console.log('Generating PDF');
				var pdf = new jsPDF();
		
				pdf.text(alertDesc, 10, 10);
				var pdfName = "alert"+alertId+".pdf";
				pdf.save(pdfName);
				pdfGenerated=true;
				document.getElementById('generatedPDF').files = pdf;
			} else {
				console.log('PDF exists - attaching');
			}
		} else {
			console.log('PDF not being attached');
		}
	}
	var pdfGenerated = false;
	var alertId = Scopes.get('newMessageRoute').alertId;
	
	// this way of getting the alert description is bad and nasty
	var messageAlert = Scopes.get('alertsCtrl').selectedAlert;
	
	//$http.get("http://localhost:8080/alert/getAlertById/"+alertId).then(function(response) {
	//	var messageAlert = response.data;
	//});
	
	var attachments = document.getElementById("attachments");
	if ('files' in attachments){
		if (attachments.files.length > 0) {
			document.getElementById('processedAttachments').value = attachments;
		}
	}
	
	var alertDesc = messageAlert.desc;
	document.getElementById('attachPDF').onclick = function() {
		if(this.checked){
			if(!pdfGenerated){
				console.log('Generating PDF');
				var pdf = new jsPDF();
		
				pdf.text(alertDesc, 10, 10);
				var pdfName = "alert"+alertId+".pdf";
				pdf.save(pdfName);
				pdfGenerated=true;
				document.getElementById('generatedPDF').value = pdf;
			} else {
				console.log('PDF exists - attaching');
			}
		} else {
			console.log('PDF not being attached');
		}
	}
});

alertTool.controller('viewMessageCtrl', function($scope, $http, Scopes){
 	//$http.get("http://localhost:8080/rfi/fulllist").then(function(response) {
    //	$scope.messages = response.data;
    //});

$http.get("http://localhost:8080/attachment/attachmentListFullDummy").then(function(response) {
                $scope.files = response.data;
            });


    $scope.messages=Scopes.get('messageListCtrl').filtMessages;

	function isMessage(message){
		return message.messageId === Scopes.get('viewMessageRoute').messageId;
	}
	
   $scope.selectedMessage = $scope.messages.find(isMessage);
 
});


alertTool.controller('messageListCtrl', function($scope, $http, Scopes){
  	Scopes.store('messageListCtrl', $scope);
  	
  	$http.get("http://localhost:8080/message/messagesListFullDummy").then(function(response) {
        $scope.messages = response.data;
 	}).finally(function(messages){
 	   $scope.filtMessages = $scope.messages.filter(function (message){
            return message.conversationId===Scopes.get('messageListRoute').conversationId;
    	});
	});
	
    $scope.selId = -1;
    $scope.selMessage = function (message, id){
       $scope.selectedMessage=message;
       $scope.selId=id;
       var messageHref='#/message/'+$scope.selectedMessage.messageId;
       document.getElementById("viewMessageHref").href=messageHref;
       document.getElementById("viewMessageHref").classList.remove('greyOut');
       document.getElementById("viewMessageHref").classList.add('enable');
    }
			
    $scope.isSelMessage=function(message){
        return $scope.selMessage===message;
    }

    $scope.orderByMe=function(x){       
        $scope.myOrderBy=x;
    }
});



alertTool.controller('templatesCtrl', function($scope, $http, Scopes){


    $http.get("").then(function(response) {
        $scope.templates = response.data;
    });
     /**
     $scope.templates=[
        { templateTitle: 'Expansion Test', questions: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.Vestibulum posuere blandit venenatis. Nam non dui et felis condimentum cursus et nec velit. Sed quis dapibus metus, in interdum mauris. Fusce auctor sodales mauris, vitae interdum tellus pellentesque id. Duis at fermentum lacus. Morbi ullamcorper ante eget dolor lobortis semper. Donec volutpat urna eget ligula vestibulum, auctor luctus neque posuere. Nullam purus turpis, accumsan eget felis varius, sollicitudin placerat dolor.'},
        { templateTitle: 'Equities', questions: 'Not an ideal field for questions'},
        { templateTitle: 'TPS', questions: 'but if going down this route'},
        { templateTitle: 'Rates basic', questions: 'could potentially set up display as expandable '},
        { templateTitle: 'Default', questions: 'and limit autodisplayed content to 1line'},
        { templateTitle: 'Expansion Test', questions: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.Vestibulum posuere blandit venenatis. Nam non dui et felis condimentum cursus et nec velit. Sed quis dapibus metus, in interdum mauris. Fusce auctor sodales mauris, vitae interdum tellus pellentesque id. Duis at fermentum lacus. Morbi ullamcorper ante eget dolor lobortis semper. Donec volutpat urna eget ligula vestibulum, auctor luctus neque posuere. Nullam purus turpis, accumsan eget felis varius, sollicitudin placerat dolor.'},
        { templateTitle: 'Equities', questions: 'Not an ideal field for questions'},
        { templateTitle: 'TPS', questions: 'but if going down this route'},
        { templateTitle: 'Rates basic', questions: 'could potentially set up display as expandable '},
        { templateTitle: 'Default', questions: 'and limit autodisplayed content to 1line'},
        { templateTitle: 'Expansion Test', questions: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.Vestibulum posuere blandit venenatis. Nam non dui et felis condimentum cursus et nec velit. Sed quis dapibus metus, in interdum mauris. Fusce auctor sodales mauris, vitae interdum tellus pellentesque id. Duis at fermentum lacus. Morbi ullamcorper ante eget dolor lobortis semper. Donec volutpat urna eget ligula vestibulum, auctor luctus neque posuere. Nullam purus turpis, accumsan eget felis varius, sollicitudin placerat dolor.'},
        { templateTitle: 'Equities', questions: 'Not an ideal field for questions'},
        { templateTitle: 'TPS', questions: 'but if going down this route'},
        { templateTitle: 'Rates basic', questions: 'could potentially set up display as expandable '},
        { templateTitle: 'Default', questions: 'and limit autodisplayed content to 1line'}
    ];
    **/


    // allows user to append html to the new template form.

               $count = 0;
               $max = 6;
               $(function () {
                   $("#addField").click(function() {
                   $count += 1;
                   if($count >= $max)
                            return;
                   $("#tempScreenTop").append('<b class="Q/F"> Field </b>' + $count + ':  ' + '<textarea class="templateMainBody" type="text" cols="30" rows="5"></textarea><br><br><br><br><br><br>');
                   })

        });

 });

 alertTool.controller('templateViewCtrl', function($scope, $http) {

 });

