def taskCount

pipeline {
    agent any
    stages {
        stage('init_pipeline') {
            steps {
                echo "remote repo url is: ${env.GIT_URL}"
                echo "remote repo branch is: ${env.GIT_BRANCH}"
                sh 'printenv'
            }
        }
        stage('collect_parameter') {
            options {
                timeout(time: 1, unit: 'HOURS')
            }
            input {
                message "input test task count"
                ok "ok"
//                submitter "alice,bob"
                parameters {
                    choice(choices: '1\n3\n5\n7\n10', description: '', name: 'testTaskCount')
                }
            }
            steps {
                script {
                    taskCount = sh(script: 'echo ${testTaskCount}', returnStdout: true).trim()
                }
            }
        }
        stage('parallel_test') {
            steps {
                script {
                    def tasks = [:]
                    def tc = taskCount as int
                    for (int i = 0; i < tc; i++) {
                        tasks[i] = {
                            stage("exec_test") {
                                sh "mvn clean test -DcaseFile=case -DtaskCount=${taskCount}"
                            }
                        }
                    }
                    parallel tasks
                }
            }
        }
    }
}
