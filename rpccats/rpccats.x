/* XDR eXtrernal Data Representation */

struct Cat {
	string name<>;
	int age;
	char sex;
};

struct ListOfCats {
	Cat cats<>;
};

program RPCCATSPROG {
	version RPCCATSVERS1 {
    	ListOfCats LIST(void) = 1;
        bool ADD(Cat) = 2;
 	} = 1;
	version RPCCATSVERS2 {
    	ListOfCats LIST(void) = 1;
        bool ADD(Cat) = 2;
        int COUNT(void) = 3;
 	} = 2;
} = 0x20000ca1;
