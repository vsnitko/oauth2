import {useLocation, useRoute} from "wouter";
import {useEffect} from "react";


export default function User({id}: { id: string }) {

    useEffect(() => {
        console.log(id);
    }, []);

    return <>
        AAA + {id}
    </>;
}
