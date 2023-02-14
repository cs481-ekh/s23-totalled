./gradlew build || ( echo "FAILED --  test_build"; exit 1;)
./gradlew packageUberJarForCurrentOS || ( echo "FAILED --  test_build"; exit 1;)
