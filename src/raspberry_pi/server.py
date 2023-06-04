import flask
import serial
import time

app = flask.Flask(__name__)
arduino = serial.Serial("/dev/ttyACM0", 9600, timeout=1)
status = False

@app.route('/', methods=['GET', 'POST'])
def handle_request():
    if flask.request.method == 'POST':
        is_on = receive() # Estado da lampada dado pelo arduino

        data = flask.request.get_json()

        if data['isOn'] == is_on:
            return flask.jsonify({'isOn': is_on})

        print(f'Current: {is_on} | POST: {data["isOn"]}')

        send(data['isOn'])

        is_on = receive() # Estado da lampada dado pelo arduino

        return flask.jsonify({'isOn': is_on})

    elif flask.request.method == 'GET':
        is_on = receive()
        print("GET:", is_on)

        return flask.jsonify({'isOn': is_on})

def send(is_on):
            global arduino
            time.sleep(0.1)
            if arduino.isOpen():
                print("{} connected!".format(arduino.port))
                try:
                    if is_on:
                        arduino.write(str("1").encode())
                    else:
                        arduino.write(str("0").encode())
                    arduino.flushInput()
                except KeyboardInterrupt:
                    print("KeyboardInterrupt")

def receive():
        global arduino
        global status
        time.sleep(0.1)
        if arduino.isOpen():
            print("{} connected!".format(arduino.port))
            try:
                time.sleep(0.1)
                if arduino.inWaiting() == 0:
                    return status

                while arduino.inWaiting() > 0:
                    response = arduino.readline()
                    print("RECEIVED: ", response)
                print("RECEIVED LAST: ", response)

                if response.decode().find("ON") == 0:
                    status = True
                    return status
                elif response.decode().find("OFF") == 0:
                    status = False
                    return status

                arduino.flushInput()

            except KeyboardInterrupt:
                    print("KeyboardInterrupt")

app.run(host="0.0.0.0", port=5000, debug=True)
