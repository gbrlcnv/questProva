'use strict';

describe('Controller Tests', function() {

    describe('SchemaAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSchemaAnswer, MockSchema1, MockPerson, MockSchemaAnswerField;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSchemaAnswer = jasmine.createSpy('MockSchemaAnswer');
            MockSchema1 = jasmine.createSpy('MockSchema1');
            MockPerson = jasmine.createSpy('MockPerson');
            MockSchemaAnswerField = jasmine.createSpy('MockSchemaAnswerField');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SchemaAnswer': MockSchemaAnswer,
                'Schema1': MockSchema1,
                'Person': MockPerson,
                'SchemaAnswerField': MockSchemaAnswerField
            };
            createController = function() {
                $injector.get('$controller')("SchemaAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'questProvaApp:schemaAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
