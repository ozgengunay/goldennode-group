/*
 ============================================================================
 Name        : Ops.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "ops.h"

void process(operation *oper);

int items;
operation *oper;


int main(void) {

	for (int i = 0; i < items; i++)
		process(oper);

	return EXIT_SUCCESS;
}



void process(operation *oper) {
	for (int i = 0; i < 6; i++) {
		for (int j = i + 1; j < 6; j++) {

		}
	}
}

