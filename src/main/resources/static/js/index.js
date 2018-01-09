var app = angular.module('vacApp', []);
app.controller('writeCtrl', function ($scope, $http) {
    $scope.days = 0;
    $scope.reason = "aaaa";

    $scope.applyVac = function () {
        $http.post(
            "/startVac",
            {
                "days" : $scope.days,
                "reason" : $scope.reason
            }
        ).then(function (response) {
            if (response.data == true) {
                alert("操作成功！")
            }
        })
    }

});
app.controller('myVacCtrl', function ($scope, $http) {

    $scope.vacList = [{},{},{}];

    $scope.myVac = function () {
        $http.get(
            "/myVac"
        ).then(function (response) {
            $scope.vacList = response.data;
        })
    }

});

app.controller("myAudit", function ($scope, $http) {
    $scope.vacTaskList = [];

    $scope.myAudit = function () {
        $http.get(
            "/myAudit"
        ).then(function (response) {
            $scope.vacTaskList = response.data;
        })
    };

    $scope.passAudit = function (taskId) {
        $http.post(
            "/passAudit",
            {
                "id" : taskId
            }
        ).then(function (response) {
            if (response.data == true) {
                alert("操作成功！")
            } else {
                alert("操作失败！")
            }
        })
    }
});
