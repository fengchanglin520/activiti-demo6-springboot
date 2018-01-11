var app = angular.module("userApp", []);
app.controller("userCtrl", function ($scope, $http) {

    $scope.userList = [];
    $scope.groupList = [];
    $scope.groupUserList = [];
    $scope.currentGroup = "";

    $scope.getAllUser = function () {
        $http.get(
            "/getAllUser"
        ).then(function (response) {
            $scope.userList = response.data;
        })
    };

    $scope.getAllGroup = function () {
        $http.get(
            "/getAllGroup"
        ).then(function (response) {
            $scope.groupList = response.data;
        })
    };

    $scope.getGroupUser = function (groupId) {
        $scope.currentGroup = groupId;
        $http.get(
            "/getUserGroup?groupId=" + groupId
        ).then(function (response) {
            $scope.groupUserList = response.data;
        })
    };


});