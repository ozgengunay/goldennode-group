#include <stdio.h>
#include <stdlib.h>
#include "array.h"
#include "ops.h"

int arraySize;
operation *ptr;

void initArray(int size) {

	ptr = (operation *) malloc(size * sizeof(operation *));
	if (ptr == NULL) {
		printf("error allocating pointer. Line %d", __LINE__);
		exit(1);
	}

}

void removeIt(operation *item) {
	//

}

void addItem(operation *item) {
	ptr[arraySize++] = *item;
}

int sizeOfArray();
void clearArray();
