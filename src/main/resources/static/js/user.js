var app = angular.module("userApp", []);
app.controller("userCtrl", function ($scope, $http) {

    $scope.userList = [];
    $scope.groupList = [];
    $scope.groupUserList = [];

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

    $scope.getGroupUser = function (group) {
        $scope.currentGroup = group.name + "(" + group.id + ")";
        $http.get(
            "/getUserGroup?groupId=" + group.id
        ).then(function (response) {
            $scope.groupUserList = response.data;
        })
    };


});