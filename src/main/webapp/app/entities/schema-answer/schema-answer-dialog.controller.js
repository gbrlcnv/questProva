(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaAnswerDialogController', SchemaAnswerDialogController);

    SchemaAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SchemaAnswer', 'Schema1', 'Person', 'SchemaAnswerField'];

    function SchemaAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SchemaAnswer, Schema1, Person, SchemaAnswerField) {
        var vm = this;

        vm.schemaAnswer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.schema1s = Schema1.query();
        vm.people = Person.query({filter: 'schemaanswer-is-null'});
        $q.all([vm.schemaAnswer.$promise, vm.people.$promise]).then(function() {
            if (!vm.schemaAnswer.person || !vm.schemaAnswer.person.id) {
                return $q.reject();
            }
            return Person.get({id : vm.schemaAnswer.person.id}).$promise;
        }).then(function(person) {
            vm.people.push(person);
        });
        vm.schemaanswerfields = SchemaAnswerField.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schemaAnswer.id !== null) {
                SchemaAnswer.update(vm.schemaAnswer, onSaveSuccess, onSaveError);
            } else {
                SchemaAnswer.save(vm.schemaAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('questProvaApp:schemaAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.completionDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
