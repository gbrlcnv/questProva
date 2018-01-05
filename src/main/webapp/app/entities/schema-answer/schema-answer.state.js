(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('schema-answer', {
            parent: 'entity',
            url: '/schema-answer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaAnswer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-answer/schema-answers.html',
                    controller: 'SchemaAnswerController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaAnswer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('schema-answer-detail', {
            parent: 'schema-answer',
            url: '/schema-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaAnswer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-answer/schema-answer-detail.html',
                    controller: 'SchemaAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaAnswer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchemaAnswer', function($stateParams, SchemaAnswer) {
                    return SchemaAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'schema-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('schema-answer-detail.edit', {
            parent: 'schema-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer/schema-answer-dialog.html',
                    controller: 'SchemaAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaAnswer', function(SchemaAnswer) {
                            return SchemaAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-answer.new', {
            parent: 'schema-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer/schema-answer-dialog.html',
                    controller: 'SchemaAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                creationDate: null,
                                completionDate: null,
                                flagComplete: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('schema-answer', null, { reload: 'schema-answer' });
                }, function() {
                    $state.go('schema-answer');
                });
            }]
        })
        .state('schema-answer.edit', {
            parent: 'schema-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer/schema-answer-dialog.html',
                    controller: 'SchemaAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaAnswer', function(SchemaAnswer) {
                            return SchemaAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-answer', null, { reload: 'schema-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-answer.delete', {
            parent: 'schema-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer/schema-answer-delete-dialog.html',
                    controller: 'SchemaAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchemaAnswer', function(SchemaAnswer) {
                            return SchemaAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-answer', null, { reload: 'schema-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
