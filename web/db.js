class LocalDB {
    constructor(entityName) {
        this.key = `db_${entityName.toLowerCase()}`;
        this.nextIdKey = `${this.key}_nextId`;

        if (!localStorage.getItem(this.key)) {
            localStorage.setItem(this.key, JSON.stringify([]));
            localStorage.setItem(this.nextIdKey, "0");
        }
    }

    _getAll() {
        return JSON.parse(localStorage.getItem(this.key) || "[]");
    }

    _saveAll(list) {
        localStorage.setItem(this.key, JSON.stringify(list));
    }

    create(obj) {
        const list = this._getAll();
        let nextId = parseInt(localStorage.getItem(this.nextIdKey) || "0") + 1;
        obj.id = nextId;
        list.push(obj);
        this._saveAll(list);
        localStorage.setItem(this.nextIdKey, nextId.toString());
        return obj.id;
    }

    read(id) {
        return this._getAll().find(item => item.id === id) || null;
    }

    readAll() {
        return this._getAll();
    }

    update(newObj) {
        const list = this._getAll();
        const index = list.findIndex(item => item.id === newObj.id);
        if (index === -1) return false;
        list[index] = newObj;
        this._saveAll(list);
        return true;
    }

    delete(id) {
        const list = this._getAll();
        const newList = list.filter(item => item.id !== id);
        if (newList.length === list.length) return false;
        this._saveAll(newList);
        return true;
    }

    clear() {
        localStorage.removeItem(this.key);
        localStorage.removeItem(this.nextIdKey);
    }

    dump() {
        return JSON.stringify(this._getAll(), null, 2);
    }

    toBytes(obj) {
        const nameBytes = obj.name.length;
        const descBytes = obj.desc.length;

        const datalens = {
            id: 4,
            namelen: 2,
            desclen: 2,
            gtin: 13,
            status: 1
        };

        const totalLength = 2 + datalens.id + datalens.namelen + nameBytes + datalens.desclen + descBytes + datalens.gtin + datalens.status;

        const buffer = new ArrayBuffer(totalLength);
        const view = new DataView(buffer);

        let offset = 0;

        // obj length
        view.setInt16(offset, totalLength - 2);
        offset += 2;

        // id
        view.setInt32(offset, obj.id);
        offset += datalens.id;

        // name length
        view.setInt16(offset, nameBytes);
        offset += datalens.namelen;

        // name
        for (let i = 0; i < nameBytes; i++) {
            view.setUint8(offset + i, obj.name.charCodeAt(i));
        }
        offset += nameBytes;

        // desc length
        view.setInt16(offset, descBytes);
        offset += datalens.desclen;

        // desc
        for (let i = 0; i < descBytes; i++) {
            view.setUint8(offset + i, obj.desc.charCodeAt(i));
        }
        offset += descBytes;

        // gtin-13
        for (let i = 0; i < datalens.gtin; i++) {
            view.setUint8(offset + i, i < datalens.gtin ? obj.gtin.charCodeAt(i) : 0);
        }
        offset += datalens.gtin;

        // status
        view.setUint8(offset, obj.status || 0);

        return new Uint8Array(buffer);
    }
}
