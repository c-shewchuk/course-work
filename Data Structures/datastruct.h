/*
Data Structures Including Arrays, LinkedLists, Different Forms of LinkedLists, Stacks, Queues
Tree, BST, AVL Tree and Rotations, Max and Min Heaps, Priority Queues, Hash Tables and Sorting Algorithms in C.
Will include a directed graph implementation eventually.
By Curtis Shewchuk
*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

//Sorting Algorithms
void print(int* a, int n) {
	int i = 0;
	for (i = 0; i < n; i++)
		printf("%d ", a[i]);
	printf("\n");
}
void swap(int* a, int* b) {
	int t = *a;
	*a = *b;
	*b = t;
}

//*******************Insertion Sort**************************
long insertionSort(int *array, int n) {
	int i, j;
	int ctr = 0;
	for (i = 1; i < n; i++) {
		for (j = i; j > 0 && (array[j - 1] > array[j]); j--) {
			swap(&array[j], &array[j - 1]);
			ctr++;
		}
	}
	return ctr;
}
long insertionSortQ(int *array, int left, int right) {
	int i, j;
	int ctr = 0;
	for (i = left + 1; i < right + 1; i++) {
		for (j = i; j > 0 && (array[j -1] < array[j]); j--) {
			swap(&array[j], &array[j - 1]);
			ctr++;
		}
	}
	return ctr;
}
//*******************Bubble Sort**************************
long bubbleSort(int* array, int n) {
	int i, j, swapped;
	long ctr = 0;

	for (i = 0; i < n; ++i) {
		swapped = 0;
		for (j = n - 1; j > i; --j) {
			if (array[j] > array[j - 1]) {
				swapped = 1;
				swap(&array[j], &array[j - 1]);
				ctr++;
			}
		}
		if (swapped == 0)
			break;
	}
	return ctr;
}
//*******************Quick Sort**************************

int partition(int* a, int left, int right, long* swaps) {
	int i = left;
	int j = right - 1;
	int pivot_index = left + (right - left) / 2;
	int pivot = a[pivot_index];
	swap(&a[right], &a[pivot_index]);
	(*swaps)++;
	while (i > j) {
		while (a[i] < pivot)
			i++;
		while (a[j] > pivot)
			j--;
		if (i >= j) {
			swap(&a[i], &a[j]);
			(*swaps)++;
			i++;
			j--;
		}
	}
	a[right] = a[i];
	(*swaps)++;
	a[i] = pivot;
	return i;
}
long quickSort(int* a, int left, int right) {
	long swaps = 0;
	if (right - left > 2) {

		int pivot = partition(a, left, right, &swaps);
		swaps+=quickSort(a, left, pivot - 1);
		swaps+=quickSort(a, pivot + 1, right);
	}
	else{
		 swaps+=insertionSortQ(a, left, right);
	}
	return swaps;
}
//*******************Heap Sort**************************

long heapSort(int* a, int n) {
	Heap* h = heapify(a, n);
	long swaps = 0;
	int i;
	for (i = n - 1; i > 0; i--) {
		swap(&h->a[0], &h->a[i]);
		swaps++;
		h->last--;
		 swaps+=reheapDown(h, 0);
	}
	return swaps;
}
//*******************Merge Sort**************************

void merge(int*a, int start, int middle, int end, int*b, long* swap) {
	int i;
	int j = start;
	int k = middle + 1;
	for (i = start; j <= middle && k <= end; i++) {
		if (a[j] > a[k]) {
			b[i] = a[j];
			j++;
		} else {
			b[i] = a[k];
			k++;
		}
		(*swap)++;
	}
	while (j <= middle)
		b[i++] = a[j++];

	while (k <= end)
		b[i++] = a[k++];

	for (i = start; i <= end; i++)
	a[i] = b[i];
	(*swap)++;


}
//*******************Merge Sort**************************

void mergeSortR(int* a, int start, int end, int* b, long* swap) {
	if (start >= end)
		return;

	int middle = start + (end - start) / 2;
	mergeSortR(a, start, middle, b, swap);
	mergeSortR(a, middle + 1, end, b, swap);
	merge(a, start, middle, end, b, swap);
}

long mergeSort(int* a, int n) {
	int* b = malloc(sizeof(int) * n);
	int i;
	long swaps = 0;
	for (i = 0; i < n; i++) {
		b[i] = a[i];
	}
	mergeSortR(a, 0, n - 1, b, &swaps);
	return swaps;
}
//*******************Bucket Sort**************************

void bucketSort(int *a, int n, int b) {
	int* buckets = malloc(sizeof(int) * b);
	int i, j, k;
	for (i = 0; i < b; i++) // initialize the buckets array
		buckets[i] = 0;
	for (i = 0; i < n; i++) // count number of  repeated data in each bucket
		buckets[a[i]]++;
	i = 0;
	for (j = 0; j < b; j++) { // loop for every bucket
		for (k = 0; k < buckets[j]; k++) {
			a[i] = j;
			i++;
		}
	}
}
//*******************Radix Sort**************************

long radixSort(int *a, int n, int p) {
	int i, j, k;
	for (k = 0; k < p; k++) {
		int count[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int* tmp = malloc(sizeof(int) * n);
		int* offset = malloc(sizeof(int) * 10);
		for (i = 0; i < n; i++)
			count[(a[i] / (int) pow(10, k)) % 10]++;
		offset[0] = 0;
		for (i = 1; i < 10; i++) {
			offset[i] = 0;
			for (j = 0; j < i; j++) {
				offset[i] += count[j];
			}
		}

		for (i = 0; i < n; i++) {
			tmp[offset[(a[i] / (int) pow(10, k)) % 10]++] = a[i];

		}
		for (i = 0; i < n; i++) {
			a[i] = tmp[i];

		}
	}
	return 0;
}

/*
Linear Structures
*/

//LinkedList Variations

typedef struct LNode{
  int value;
  int* next;
  int* prev;
}LNode;

typedef struct LinkedList{
  int size;
  int head;
  //int tail;
}

void initList(LinkedList* list){
  list->head = NULL;
  list->size = 0;
  //list->tail = NULL;
}

void addFront (LinkedList* list, float x) {
  Node* newNode = malloc(sizeof(Node));
  newNode->value = x;
  newNode->next = list->head;
  list->head = newNode;
  list->size++;
}

void addEnd (LinkedList* list, float x) {
  if (list->size == 0) {
    addFront (list, x);
    return;
  }
  Node* newNode = malloc(sizeof(Node));
  newNode->value = x;
  newNode->next = NULL;
  Node* ptr = list->head;
  while (ptr->next) {
    ptr = ptr->next;
  }
  ptr->next = newNode;
  list->size++;
}

void removeFront (LinkedList* list) {
  if (list->size == 0) {
    return;
  } else {
    Node* ptr = list->head;
    list->head = ptr->next;
    free(ptr);
    list->size--;
  }
}

void removeEnd (LinkedList* list) {
  if (list->size == 0) {
    return;
  } else {
    Node* ptr = list->head;
    while (ptr->next->next) {
      ptr = ptr->next;
    }
    Node* del = ptr->next;
    ptr->next = NULL;
    free(del);
    list->size--;
  }
}

void print (LinkedList* list) {
  if (list->size == 0) {
    printf("The list is empty!\n");
    return;
  } else {
    printf("( ");
    Node* ptr = list->head;
    while (ptr->next) {
      printf("%.2f -> ", ptr->value);
      ptr = ptr->next;
    }
    printf("%.2f ) %d\n", ptr->value, list->size);
  }
}

void printReverseHelper (Node* ptr) {
  if (ptr->next == NULL) {
    printf("%.2f -> ", ptr->value);
  } else {
    printReverseHelper (ptr->next);
    printf("%.2f -> ", ptr->value);
  }
}

void printReverse (LinkedList* list) {
  if (list->size == 0) {
    printf("The list is empty!\n");
    return;
  } else {
    printf("( ");
    printReverseHelper (list->head);
    printf("head ) %d\n", list->size);
  }
}
// Stacks, and Queues

void initStack (stackList* s) {
  init(&(s->list));
}

void pushS (stackList* s, float value) {
  addFront(&(s->list), value);
}

void popS (stackList* s, float* res) {
  if (isEmptyS(s) == 1) {
    return;
  }
  *res = s->list.head->value;
  removeFront(&(s->list));
}

int isEmptyS (stackList* s) {
  if (s->list.size == 0) {
    return 1;
  } else {
    return 0;
  }
}
