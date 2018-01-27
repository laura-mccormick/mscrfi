var alertTool = angular.module('alertTool', ['ngRoute']);

alertTool.config(function ($routeProvider){
    $routeProvider

    .when('/', {
        templateUrl: 'alertList.html',
        controller: 'alertRoute'
     })

    .when('/conversation', {
        templateUrl: 'conversation.html',
        controller: 'conversationRoute'
     })

    .when('/conversation/:alertId', {
             templateUrl: 'conversation.html',
             controller: 'conversationRoute'
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


alertTool.controller('viewMessageRoute', function($scope, $routeParams){
    $scope.messageId = $routeParams.alertId;
});

/*
alertTool.controller('templateRoute', function($scope, $routeParams, Scopes){
    Scopes.store('templateRoute', $scope);
    $scope.templateTitle = $routeParams.templateTitle;
*/

alertTool.controller('alertsCtrl', function($scope, $http){


    // Dummy data from URL
     $http.get("/alert/fullListDummy").then(function(response) {
            $scope.alerts = response.data;
        });

    //// Actual data from DB - wont work on citi
    // $http.get("/alert/fullListActual").then(function(response) {
    //        $scope.alerts = response.data;
    //    });


    // Dummy data from JS
    //    $scope.alerts=[
    //        {id: "1", desc: "Wash EMEA", hasRfi: "true"},
    //        {id: "2", desc: "Wash NAM", hasRfi: "true"},
    //        {id: "3", desc: "Ramping EMEA", hasRfi: "false"},
    //        {id: "4", desc: "Spoofing EMEA", hasRfi: "true"}
    //    ];

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

});

alertTool.controller('conversationCtrl', function($scope, $http, Scopes){

    $scope.hasRfi=true;

    window.onload = function(){
        filterByAlertId();
    }

// Dummy data from URL
 $http.get("http://localhost:8080/rfi/fullListDummy").then(function(response) {
        $scope.messages = response.data;
        if messages.length < 1 {
            hasRfi = false;
        }
    }).finally(function(messages){

    $scope.filtMessages = $scope.messages.filter(function (message){
            return message.alertId===Scopes.get('conversationRoute').alertId;
        })
    });

//// Actual data from DB
//   $http.get("/rfi/fullListActual").then(function(response) {
//           $scope.messages = response.data;
//       });

   // App currently uses dummy data:
   // For some reason you have to keep this open for the dummy data to populate.. Still dont understand why but hey.
//    $scope.messages=[
//        { messageId: 'abc', alertId: '1', sender: 'laura@citi.com', recipient: 'jake@citi.com', body: 'The first e-mail', timestamp: '01/01/2018'},
//        { messageId: 'bcd', alertId: '1', sender: 'jake@citi.com', recipient: 'laura@citi.com', body: 'Then the first reply', timestamp: '02/01/2018'},
//        { messageId: 'cde', alertId: '2', sender: 'laura@citi.com', recipient: 'jake@citi.com', body: 'Here is another email', timestamp: '03/01/2018'},
//        { messageId: 'def', alertId: '2', sender: 'jake@citi.com', recipient: 'laura@citi.com', body: 'And the second reply', timestamp: '04/01/2018'}
//    ];

//    $scope.filtMessages = $scope.messages.filter(function (message){
//        return message.alertId===Scopes.get('conversationRoute').alertId;
//    });

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

alertTool.controller('newMessageCtrl', function($scope, $http, Scopes){

    window.onload = function() {
    //filterByAlertId();
    };
    angular.module('app', [], function() {})
    FileUploadCtrl.$inject = ['$scope']
    function FileUploadCtrl(scope) {



        scope.setFiles = function(element) {
        scope.$apply(function(scope) {
          console.log('files:', element.files);
          // Turn the FileList object into an Array
            scope.files = []
            for (var i = 0; i < element.files.length; i++) {
              scope.files.push(element.files[i])
            }
          scope.progressVisible = false
          });
        };

        scope.uploadFile = function() {
            var fd = new FormData()
            for (var i in scope.files) {
                fd.append("uploadedFile", scope.files[i])
            }
            var xhr = new XMLHttpRequest()
            xhr.upload.addEventListener("progress", uploadProgress, false)
            xhr.addEventListener("load", uploadComplete, false)
            xhr.addEventListener("error", uploadFailed, false)
            xhr.addEventListener("abort", uploadCanceled, false)
            xhr.open("POST", "/fileupload")
            scope.progressVisible = true
            xhr.send(fd)
        }

        function uploadProgress(evt) {
            scope.$apply(function(){
                if (evt.lengthComputable) {
                    scope.progress = Math.round(evt.loaded * 100 / evt.total)
                } else {
                    scope.progress = 'unable to compute'
                }
            })
        }

        function uploadComplete(evt) {
            /* This event is raised when the server send back a response */
            alert(evt.target.responseText)
        }

        function uploadFailed(evt) {
            alert("There was an error attempting to upload the file.")
        }

        function uploadCanceled(evt) {
            scope.$apply(function(){
                scope.progressVisible = false
            })
            alert("The upload has been canceled by the user or the browser dropped the connection.")
        }
    }



});

alertTool.controller('viewMessageCtrl', function($scope, $http){
    // TODO This code will be used to retrieve a message from a JSON object
 $http.get("http://localhost:8080/rfi/fulllist").then(function(response) {
           $scope.messages = response.data;
       });
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


