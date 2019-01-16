/*
 * array.h
 *
 *  Created on: Jan 16, 2019
 *      Author: gunayo
 */

#ifndef ARRAY_H_
#define ARRAY_H_
#include "ops.h"

void initArray(int size);
void resizeArray(int size);
void addItem(operation *item);
void removeIt(operation *item);
int sizeOfArray();
void clearArray();
int containsItem();

#endif /* ARRAY_H_ */
