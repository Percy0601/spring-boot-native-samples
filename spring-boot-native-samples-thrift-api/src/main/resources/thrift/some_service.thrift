namespace java io.samples.thrift.api
struct User {
    1: required i32 userId;
    2: required string username;
    3: optional map<string, string> desc;
}

service SomeService {
	string echo(1: string msg);
    i32 addUser(1: required User user);
    list<User> findUserByIds(1: list<i32> idList);
}

service SomeService2 {
	string echo2(1: string msg);
    i32 addUser2(1: required User user);
    list<User> findUserByIds2(1: list<i32> idList);
}