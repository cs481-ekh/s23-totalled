./gradlew build || ( echo "FAILED --  test_build"; exit 1;)
./gradlew test || ( echo "FAILED --  tests"; exit 1;)
