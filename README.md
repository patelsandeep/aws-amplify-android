<br/>
<p align="center">
  
   <h3 align="center">AWS Amplify</h3>
   <p align="center">
   <img src="https://miro.medium.com/max/1400/0*y-ZimaRh8fnftZVZ" height="200" weight="100">
  </p>
 
  <p align="center">
    AWS Amplify is a complete solution that lets frontend web and mobile developers easily build, ship, and host full-stack applications on AWS, with the flexibility to leverage the breadth of AWS services as use cases evolve. No cloud expertise needed.
    <br/>
    <br/>
  </p>
</p>



## About The Project

The Amplify open-source client libraries provide use-case centric, opinionated, declarative, and easy-to-use interfaces across different categories of cloud powered operations enabling mobile and web developers to easily interact with their backends. These libraries are powered by the AWS cloud and offer a pluggable model which can be extended to use other providers. The libraries can be used with both new backends created using the Amplify CLI and existing backend resources.

# Getting Started


## Installing the AWS Amplify CLI

To fully leverage the AWS Amplify CLI toolchain, let's install the AWS Amplify CLI. Open your Terminal, and run the following in command line:
```sh
npm install -g @aws-amplify/cli -update
```

### If you getting error while intall AWS Amplify CLI into MAC then please use this command.

```sh
sudo npm install -g @aws-amplify/cli --unsafe-perm=true
```

## Installation

Initializing the AWS Amplify Project

Next, let's initialize a new AWS Amplify project for your Android app.

navigate into your Android Studio project root in a Terminal window, and run the following:

```sh
amplify init
```

<ul>
  <li>Choose your default editor: Android Studio (or your favourite editor)</li>
  <li>Please choose the type of app that you're building: android</li>
  <li>Where is your Res directory: (app/src/main/res): Press Enter to accept the default</li>
  <li>Do you want to use an AWS profile? Y</li>
  <li>Please choose the profile you want to use: default</li>
</ul> 

CloudFormation will now run for your AWS account to configure the initial infrastructure to support your app. After it's completed, the AWS Amplify CLI toolchain will have initialized a new project and you will see a couple of new files and folders in your app's project directory: amplify and .amplifyrc. These files hold your project's configuration.

## Adding a GraphQL API, Authentication and Generate Client Code

The AWS Amplify toolchain provides us with a streamlined process for API creation, authentication and client code generation. Let's start by running the following command in your app's root directory:

```sh
amplify add api
```

Answer the following questions:

<ul>
  <li>Please select from one of the above mentioned services: GraphQL</li>
  <li>Provide API name: AWSAmplifyCRUD</li>
  <li>Choose an authorization type for the API: Amazon Cognito User Pool</li>
  <li>Do you want to use the default authentication and security configuration? Yes, use the default configuration.</li>
  <li>Do you have an annotated GraphQL schema? N</li>
  <li>Do you want a guided schema creation? Y</li>
  <li>What best describes your project: Single object with fields (e.g. “Todo” with ID, name, description)</li>
  <li>Do you want to edit the schema now? (Y/n) Y</li>  


```sh
type TODO @model {
  id: ID!
  name: String!
  description: String
}
```

<li>Go back to the Terminal, and press enter to continue.</li>
</ul>

After that , let's generate class file for model by running:

```sh
amplify codegen model
```

Next, let's push the configuration to your AWS account by running:

```sh
amplify push
```

You will be prompted with your added changes:

```sh
| Category | Resource name          | Operation | Provider plugin   |
| -------- | ---------------------- | --------- | ----------------- |
| Auth     | cognito12412212        | Create    | awscloudformation |
| Api      | AWSAmplifyCRUD.        | Create    | awscloudformation |
```

<ul>
<li>Are you sure you want to continue? (Y/n) Y </li>
</ul>
Now you will be prompted to generate code for your brand new API:

<ul>
<li>Do you want to generate code for your newly created GraphQL API (Y/n) Y</li>
<li>Enter the file name pattern of graphql queries, mutations and subscriptions (app/src/main/graphql/**/*.graphql): Press Enter to accept the default</li>
<li>Do you want to generate/update all possible GraphQL operations - queries, mutations and subscriptions (Y/n) Y</li>
</ul>

CloudFormation will run again to update the newly created API and authentication mechanism to your AWS account. This process may take a few minutes.

After CloudFormation completes updating resources in the cloud, you will be given a GraphQL API endpoint, and generated GraphQL statements will be available in your project.


## Our backend is ready.

# Sample Video :-

![ezgif com-gif-maker](https://user-images.githubusercontent.com/89840075/208842050-69622605-6d22-46b7-b3e4-1edf86c92c13.gif)






