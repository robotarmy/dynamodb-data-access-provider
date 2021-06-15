/*
 *  Copyright 2020 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.curity.dynamoDBDataAccessProvider.configuration

import se.curity.identityserver.sdk.config.Configuration
import se.curity.identityserver.sdk.config.OneOf
import se.curity.identityserver.sdk.config.annotation.Description
import se.curity.identityserver.sdk.service.ExceptionFactory
import se.curity.identityserver.sdk.service.Json
import java.util.Optional


interface DynamoDBDataAccessProviderDataAccessProviderConfig: Configuration
{
    @Description("The AWS Region where DynamoDB is deployed.")
    fun getAwsRegion(): AWSRegion

    @Description("Choose how to access DynamoDB")
    fun getDynamodbAccessMethod(): AWSAccessMethod

    interface AWSAccessMethod : OneOf
    {
        val accessKeyIdAndSecret: AccessKeyIdAndSecret?
        val aWSProfile: AWSProfile?
        val aWSDirect: AWSDirect?

        interface AWSDirect
        {
          @get:Description("Hostname for direct connection, ex. http://localhost:8080 ")
          val hostname: String
          val accessKeyId: String
          val accessKeySecret: String
        }

        interface AccessKeyIdAndSecret
        {
            val accessKeyId: Optional<String>
            val accessKeySecret: Optional<String>

            @get:Description("Optional role ARN used when requesting temporary credentials, ex. arn:aws:iam::123456789012:role/dynamodb-role")
            val awsRoleARN: Optional<String>
        }

        interface AWSProfile
        {
            @get:Description("AWS Profile name. Retrieves credentials from the system (~/.aws/credentials)")
            val awsProfileName: Optional<String>

            @get:Description("Optional role ARN used when requesting temporary credentials, ex. arn:aws:iam::123456789012:role/dynamodb-role")
            val awsRoleARN: Optional<String>
        }

        @get:Description("EC2 instance that the Curity Identity Server is running on has been assigned an IAM Role with permissions to DynamoDB.")
        val isEC2InstanceProfile: Optional<Boolean>
    }

    fun getExceptionFactory(): ExceptionFactory

    fun getJsonHandler(): Json
}
