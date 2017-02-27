
The opencv Java machine learning development environment is implemented using Docker.

NOTICE 1: The face images for the svm face classification are from http://www.cl.cam.ac.uk/research/dtg/attarchive/facedatabase.html.

NOTICE 2: build.sbt is used instead of pom.xml.


Please follow the instructions below to run the svm face classfication example.

[1] download (or git clone) this source code folder.

[2] cd downloaded-source-code-folder.

[3] sudo make BIND_DIR=.  shell

	wait ... wait .... then a bash shell will be ready (root@8f07a92497e7:/#)

[4] root@8f07a92497e7:/# cd /home/machinelearning/opencv

[5] root@8f07a92497e7:/home/machinelearning/opencv# cd build 

[6] root@8f07a92497e7:/home/machinelearning/opencv/build# cmake -DCMAKE_C_COMPILER=/usr/bin/gcc-4.8 -DCMAKE_CXX_COMPILER=/usr/bin/g++-4.8  -DBUILD_EXAMPLES=on -DCMAKE_INSTALL_PREFIX=/opt  DOPENCV_EXTRA_MODULES_PATH=../../opencv_contrib/modules  ..

[7] root@8f07a92497e7:/home/machinelearning/opencv/build# make

[8] root@8f07a92497e7:/home/machinelearning/opencv/build# make install

[9] root@8f07a92497e7:/home/machinelearning/opencv/build# cd ../..

[10] root@8f07a92497e7:/home/machinelearning# cd examples/svm_face_classification/

[11] root@8f07a92497e7:/home/machinelearning/examples/svm_face_classification# cp /opt/share/OpenCV/java/opencv-320.jar ./lib/

[12] root@8f07a92497e7:/home/machinelearning/examples/svm_face_classification# sbt clean compile (you may need to try twice to get rid of an error)

[13] root@8f07a92497e7:/home/machinelearning/examples/svm_face_classification# sbt clean package (you may need to try twice to get rid of an error)

[14] root@8f07a92497e7:/home/machinelearning/examples/svm_face_classification# scala -cp ./lib/opencv-320.jar ./target/scala-2.11/svm_face_classification_2.11-1.0.jar

[15] the output looks like

	label = 2 <- 34_2.pgm
	label = 2 <- 32_2.pgm
	label = 1 <- 11_1.pgm
	label = 2 <- 33_2.pgm
	label = 2 <- 31_2.pgm
	label = 1 <- 10_1.pgm
	label = 2 <- 36_2.pgm
	label = 1 <- 17_1.pgm
	label = 1 <- 15_1.pgm
	label = 2 <- 38_2.pgm
	label = 1 <- 16_1.pgm
	label = 2 <- 37_2.pgm
	label = 1 <- 12_1.pgm
	label = 1 <- 18_1.pgm
	label = 2 <- 35_2.pgm
	label = 1 <- 19_1.pgm
	label = 1 <- 13_1.pgm
	label = 1 <- 14_1.pgm
	... in the svm.cpp .... 

 	predicted_label = 2.0

	... the predition is corret ... 


[16] To remove the message that says "... in the svm.cpp ....", open opencv/modules/ml/src/svm.cpp and comment out line # 1609 (in the local machine or in Docker). 

[17] repeat from the step [8].

