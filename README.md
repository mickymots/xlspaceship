# play-framework-scala-seed

![](https://playframework.com/assets/images/logos/play_full_color.png)

## Development

To run a local server execute:

    ./scripts/server.sh

## Tests

To run the complete test suite execute:

    ./scripts/tests.sh

## Production

### Binary

To build a binary version of the application execute:

    ./scripts/build.sh

Your package will be ready in `./target/universal/scala-dci-<VERSION>.zip`

###

However, if you just want to complie the application to be in place execute:

    ./scripts/production.sh

You will find the packaged application in `./target/universal/stage directory`. In this folder you can run `./bin/scala-dci` script that runs the application.

## Deployment

If you want to have the application deployed to Heroku you can use the following button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

To deploy the application anytime later run:

    ./scripts/deploy.sh